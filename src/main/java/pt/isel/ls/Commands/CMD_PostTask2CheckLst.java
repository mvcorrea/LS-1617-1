package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

// command sample: POST /checklists/2/tasks name=task1&description=my+task+descr&dueDate=20160920+1400

public class CMD_PostTask2CheckLst implements CommandInterface {
    public static String pattern = "(POST /checklists/\\d+/tasks)";

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con, RequestParser par) throws SQLException, ParseException {  // TODO: what to do when task dueDate > checklist dueDate ???
        String query1 = "SELECT * FROM chklst WHERE chkId = ?";
        String query2 = "INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (?, 0, ?, ?, ?)";

        int chkId = Integer.parseInt(par.getPath()[1]);

        PreparedStatement preparedStatement = con.prepareStatement(query1);
        preparedStatement.setInt(1, chkId);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            preparedStatement = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, chkId);
            preparedStatement.setString(2, par.getParams().get("name"));
            preparedStatement.setString(3, par.getParams().get("description"));
            preparedStatement.setTimestamp(4, str2ts(par.getParams().get("dueDate")));

            preparedStatement.execute();
            ResultSet rs1 = preparedStatement.getGeneratedKeys();
            int tskId = rs1.next() ? rs1.getInt(1) : 0;
            System.out.println("created Task with id: "+ tskId +" on checklist: "+ chkId);
        } else System.out.println("unable to find checklist with id: "+chkId);
    }

    public Timestamp str2ts(String datetime) throws ParseException, ParseException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd+hhmm");
        Date parsedDate = dateFormat.parse(datetime);
        return new java.sql.Timestamp(parsedDate.getTime());
    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override public String toString(){
        return "POST /checklists/{cid}/tasks - creates a new task in the checklist 'cid'.\n";
    }
}
