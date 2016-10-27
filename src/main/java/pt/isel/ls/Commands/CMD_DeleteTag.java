package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CMD_DeleteTag implements CommandInterface {
    public static String pattern = "(DELETE /tags/\\d+)";
    public static int tagId;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, GenericException{
        String query = "DELETE FROM tag WHERE tagid = ?";
        tagId = Integer.parseInt(par.getPath()[1]);

    try {
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, tagId);

        ps.executeUpdate();

        System.out.println("tag ["+ tagId +"] was deleted");

        // TODO: prior to the exeption verify if references still active

    } catch (SQLException e){
        throw new GenericException("SQL: "+ e.getMessage());
    }
        return null;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "CMD_DeleteTag{}";
    }
}
