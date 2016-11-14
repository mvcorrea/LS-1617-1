package pt.isel.ls.Commands;


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

public class CMD_PostTag implements CommandInterface{
    public static String pattern = "(POST /tags)";
    public RequestParser request;
    public int tagId;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query = "INSERT INTO tag (tagName, tagColor) VALUES (?, ?)";
        this.request = par;

        try {
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, par.getParams().get("name"));
            ps.setString(2, par.getParams().get("color"));

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            this.tagId = rs.next() ? rs.getInt(1) : 0;

            //System.out.println("created tag with id: "+ tagId);

            ps.close();
        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "POST /tags - creates a new tag and returns its identifier.\n";
    }

    @Override
    public Object getData() {
        return this.tagId;
    }
}
