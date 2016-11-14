package pt.isel.ls.Commands;

import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

// command sample: POST /checklists/2/tasks name=task1&description=my+task+descr&dueDate=20160920+1400

public class CMD_PostTask2CheckLst extends CMD_Generic implements CommandInterface {
    public static String pattern = "(POST /checklists/\\d+/tasks)";
    public RequestParser request;
    int tskId;
    boolean chkIsCompleted;
    Timestamp chkDueDate, tskDueDate;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, ParseException, AppException {

        String query1 = "SELECT * FROM \n" +
                        "  (SELECT * FROM chklst WHERE chkId = ? ) AS X  \n" +
                        "LEFT JOIN \n" +
                        "  task \n" +
                        "ON X.chkId = task.tskChkId AND tskIsCompleted = false\n" +
                        "ORDER BY tskDueDate DESC LIMIT 1";

        String query2 = "INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (?, ?, ?, ?)";

        this.request = par;
        int chkId = Integer.parseInt(par.getPath()[1]);

        try {

            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(query1);
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps1.setInt(1, chkId);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){
                this.chkIsCompleted = rs1.getBoolean("chkIsCompleted");
                this.chkDueDate = rs1.getTimestamp("chkDueDate");
                this.tskDueDate = par.getParams().containsKey("dueDate") ? str2ts(par.getParams().get("dueDate")) : null;

                if(!validate(par)){
                    String query3 = "UPDATE chklst SET chkDueDate = ?, chkIsCompleted = ? WHERE chkId = ?";
                    PreparedStatement ps3 = con.prepareStatement(query3);
                    ps3.setTimestamp(1, this.chkDueDate);
                    ps3.setBoolean(2, this.chkIsCompleted);
                    ps3.setInt(3, chkId);
                    ps3.executeUpdate();
                }

                ps2 = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, chkId);
                ps2.setString(2, par.getParams().get("name"));
                ps2.setString(3, par.getParams().get("description"));
                ps2.setTimestamp(4, this.tskDueDate);

                ps2.execute();
                ResultSet rs2 = ps2.getGeneratedKeys();
                int tskId = rs2.next() ? rs2.getInt(1) : 0;

                //System.out.println("created Task with id: "+ tskId +" on checklist: "+ chkId);

                this.tskId = tskId;

                con.commit();

                ps2.close();

            } else throw new DBException("unable to find checklist with id: "+chkId);

            ps1.close();

        } catch (SQLException e){ throw new DBException( e.getMessage() ); };


        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws ParseException {
        // must actualize checklist (chkIsCompleted -> false) after insertion
        if(this.chkIsCompleted){
            this.chkIsCompleted = false;
            return false;
        }
        // must update the checklist with the elder dueDate (from task)
        if(this.chkDueDate != null || this.tskDueDate != null ){
            if(this.chkDueDate == null || this.chkDueDate.getTime() < this.tskDueDate.getTime()) this.chkDueDate = this.tskDueDate;
            return false;
        }
        return true;
    }

    @Override public String toString(){
        return "POST /checklists/{cid}/tasks - creates a new task in the checklist 'cid'.\n";
    }

    @Override
    public Object getData() {
        return this.tskId;
    }
}
