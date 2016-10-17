package pt.isel.ls.Commands;

//import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

// command sample: POST /checklists name=task1&description=my+task+descr&dueDate=20160920+1400

public class CMD_PostCheckLst implements CommandInterface {
    public static String pattern = "(POST /checklists)";

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con, RequestParser par) throws SQLException, GenericException, org.json.simple.parser.ParseException, ParseException {
        String query = "INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, par.getParams().get("name"));
        preparedStatement.setString(2, par.getParams().get("description"));
        preparedStatement.setTimestamp(3, str2ts(par.getParams().get("dueDate")));
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int chkId = rs.next() ? rs.getInt(1) : 0;
        System.out.println("created post with id: "+ chkId);
    }


    public Timestamp str2ts(String datetime) throws ParseException, ParseException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd+hhmm");
        Date parsedDate = dateFormat.parse(datetime);
        return new java.sql.Timestamp(parsedDate.getTime());
    }


    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override public String toString(){
        return "POST /checklists - creates a new checklist.\n";
    }
}
