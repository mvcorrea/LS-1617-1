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

// command sample: POST /templates/3/tasks name=Graça&description="Miradouro da Graça"

public class CMD_PostTask2Template implements CommandInterface {
    public static String pattern = "(POST /templates/\\d+/tasks)";
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

        String query1 = "SELECT * FROM templ WHERE temId = ?";
        String query2 = "INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (?, ?, ?)";
        this.request = par;

        int temId = Integer.parseInt(par.getPath()[1]);

        try {

            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, temId);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){

                PreparedStatement ps2 = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, temId);
                ps2.setString(2, par.getParams().get("name"));
                ps2.setString(3, par.getParams().get("description"));

                ps2.execute();
                ResultSet rs = ps2.getGeneratedKeys();
                this.tskId = rs.next() ? rs.getInt(1) : 0;

                //System.out.println("created Task with id: "+ tskId +" on checklist: "+ temId);

                con.commit();

                ps2.close();

            } else throw new DBException("unable to find template with id: "+temId);

            ps1.close();

        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws ParseException {
        return false;
    }

    @Override public String toString(){
        return "POST /checklists/{cid}/tasks - creates a new task in the checklist 'cid'.\n";
    }

    @Override
    public Object getData() {
        return this.tskId;
    }
}
