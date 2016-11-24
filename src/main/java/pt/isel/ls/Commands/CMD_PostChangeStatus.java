package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

// POST /checklists/{cid}/tasks/{lid} - changes the state of the task identified by lid, belonging to the checklist identified by cid
// POST /checklists/1/tasks/4 isClosed=true

public class CMD_PostChangeStatus implements CommandInterface{
    public static String pattern = "(POST /checklists/\\d+/tasks/\\d+)";
    public RequestParser request;
    int tskId;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override

    public Object process(Connection con, RequestParser par) throws SQLException, AppException, ParseException, java.text.ParseException {
        // checklist with tasks
        //String query1 = "SELECT * FROM chklst JOIN task ON chklst.chkId = task.tskChkId WHERE chkId = ? AND tskId = ?";
        String query1 = "SELECT * FROM task WHERE tskChkId = ? AND tskId = ?";

        String query2 = "UPDATE task SET tskIsCompleted = ? WHERE tskId = ? AND tskChkId = ?";

        String query3 = "SELECT BIT_AND(tskIsCompleted) FROM\n" +
                "(SELECT * FROM chklst WHERE chkId = ?) AS C LEFT JOIN task ON C.chkId = tskChkId";
        String query4 = "UPDATE chklst SET chkIsCompleted = ? WHERE chkId = ?";



        this.request = par;

        int chkId = Integer.parseInt(par.getPath()[1]);
        int tskId = Integer.parseInt(par.getPath()[3]);

        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2);
        PreparedStatement ps3 = con.prepareStatement(query3);
        PreparedStatement ps4 = con.prepareStatement(query4);

        try {

            con.setAutoCommit(false);

            ps1.setInt(1, chkId);
            ps1.setInt(2, tskId);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){

                ps2.setBoolean(1, Boolean.valueOf(par.getParams().get("isClosed")));
                ps2.setInt(2, tskId);
                ps2.setInt(3, chkId);

                int count = ps2.executeUpdate();

                if (count > 0) this.tskId = tskId;

                //System.out.println("updated Task with id: "+ tskId +" on checklist: "+ chkId);

            } else throw new DBException("unable to find task: "+ tskId +" in checklist: "+chkId);


            ps3.setInt(1, chkId);
            ResultSet rs3 = ps3.executeQuery();
            rs3.next();
            boolean allTskClosed = rs3.getBoolean(1);
            //System.out.println("-> "+allTskClosed);

            // update checklist completed
            if(allTskClosed) ps4.setBoolean(1, true);
            else ps4.setBoolean(1, false);
            ps4.setInt(2, chkId);
            int chkUpd = ps4.executeUpdate();
            //System.out.println("--> " + chkUpd);

        } catch (SQLException e){
            con.rollback();
            throw new DBException( e.getMessage() );
        }finally {
            con.commit();
            ps1.close();
            ps2.close();
        };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return false;
    }

    @Override
    public String toString() {
        return "POST /checklists/{cid}/tasks/{lid} - changes the state of the task identified by lid, belonging to the checklist identified by cid.\n";
    }

    @Override
    public Object getData() {
        return this.tskId;
    }
}
