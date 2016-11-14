package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
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

public class CMD_GetCheckLstOpenDueDate_ implements CommandInterface {
    public static String pattern = "(GET /checklists/open/sorted/duedate)";
    public RequestParser request;
    public LinkedList<CheckList> cls = new LinkedList<>();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException, ParseException, java.text.ParseException {
        String query = "SELECT * FROM chklst WHERE chkIsCompleted = false ORDER BY chkDueDate";
        this.request = par;

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) cls.add(new CheckList().fill(rs));

            //cls.forEach(System.out::println);

            ps.close();
        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return false;
    }

    @Override
    public String toString() {
        return "GET /checklists/open/sorted/duedate - returns a list with all uncompleted checklists, ordered by increasing completion date.\n";
    }

    @Override
    public Object getData() {
        return cls;
    }
}
