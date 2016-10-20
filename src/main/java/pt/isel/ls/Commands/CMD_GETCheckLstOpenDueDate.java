package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

// command sample: GET /checklists/open/sorted/duedate

public class CMD_GETCheckLstOpenDueDate implements CommandInterface {
    public static String pattern = "(GET /checklists/open/sorted/duedate)";
    public LinkedList<CheckList> cls = new LinkedList<>();

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    // TODO: verify All Exception

    @Override
    public CommandWrapper process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException {
        String query = "SELECT * FROM chklst WHERE chkIsCompleted = false ORDER BY chkDueDate";

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) cls.add(new CheckList().fill(rs));

        cls.forEach(System.out::println);

        ps.close();
        return new CommandWrapper(this);
    }

    @Override
    public Object getData() {
        return cls;
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