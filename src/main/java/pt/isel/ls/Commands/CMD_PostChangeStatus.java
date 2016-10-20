package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;
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

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    // TODO: verify All Exception

    @Override
    public CommandWrapper process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException {
        String query1 = "SELECT * FROM chklst JOIN task ON chklst.chkId = task.tskChkId WHERE tskId = ?";
        String query2 = "UPDATE task SET tskIsCompleted = ? WHERE tskId = ?";

        int chkId = Integer.parseInt(par.getPath()[1]);
        int tskId = Integer.parseInt(par.getPath()[3]);

        PreparedStatement preparedStatement = con.prepareStatement(query1);
        preparedStatement.setInt(1, tskId);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            preparedStatement = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, Boolean.valueOf(par.getParams().get("isClosed")));
            preparedStatement.setInt(2, tskId);

            preparedStatement.executeUpdate();

            ResultSet rs1 = preparedStatement.getGeneratedKeys();
            //tskId = rs1.next() ? rs1.getInt(1) : 0; // NOT WORKING
            System.out.println("updated Task with id: "+ tskId +" on checklist: "+ chkId);
        } else System.out.println("unable to find task: "+ tskId +" in checklist: "+chkId);

        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override
    public String toString() {
        return "POST /checklists/{cid}/tasks/{lid} - changes the state of the task identified by lid, belonging to the checklist identified by cid\n";
    }
}
