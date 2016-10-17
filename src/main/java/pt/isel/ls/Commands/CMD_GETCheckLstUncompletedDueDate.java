package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class CMD_GETCheckLstUncompletedDueDate implements CommandInterface {
    public static String pattern = "(GET /checklists/open/sorted/duedate)";

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    // SELECT * FROM chklst LEFT JOIN task ON chklst.chkId = task.tskChkId AND chkIsCompleted = false;



    @Override
    public void process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException {
        // ?????? json doesnt have the notion of order! to maintain like this MUST sort in the client ???????
        String query1 = "SELECT * FROM chklst WHERE chkIsCompleted = false ORDER BY chkDueDate";
        // with tasks
        String query2 = "SELECT * FROM chklst JOIN task ON chklst.chkId = task.tskChkId AND chklst.chkIsCompleted = false;";

        LinkedList<CheckList> cls = new LinkedList<>();
        PreparedStatement preparedStatement = con.prepareStatement(query2);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) cls.add(new CheckList().add(rs));

        cls.forEach(System.out::println);

        preparedStatement.close();
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override
    public String toString() {
        return "GET /checklists/open/sorted/duedate - returns a list with all uncompleted checklists, ordered by increasing completion date";
    }
}
