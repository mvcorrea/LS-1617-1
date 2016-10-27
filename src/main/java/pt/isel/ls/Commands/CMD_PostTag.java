package pt.isel.ls.Commands;


import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CMD_PostTag implements CommandInterface{
    public static String pattern = "(POST /tags)";
    public int tagId;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException {
        String query = "INSERT INTO tag (tagName, tagColor) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, par.getParams().get("name"));
        ps.setString(2, par.getParams().get("color"));

        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        this.tagId = rs.next() ? rs.getInt(1) : 0;

        System.out.println("created tag with id: "+ tagId);

        ps.close();
        return null;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "POST /tags - creates a new tag and returns its identifier.\n";
    }
}
