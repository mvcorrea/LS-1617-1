package pt.isel.ls.Commands;

import pt.isel.ls.Containers.Tag;
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

public class CMD_GetTag implements CommandInterface {
    public static String pattern = "(GET /tags)";
    public RequestParser request;
    public LinkedList<Tag> tgs = new LinkedList<>();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query = "SELECT * FROM tag";
        this.request = par;

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) tgs.add(new Tag().fill(rs));

            //tgs.forEach(System.out::println);

            ps.close();
        }catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override
    public String toString() { return "GET /tags - returns the list of all tags.\n"; }

    @Override
    public Object getData() {
        return tgs;
    }
}
