package pt.isel.ls.Commands;

import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CMD_DeleteTag implements CommandInterface {
    public static String pattern = "(DELETE /tags/\\d+)";
    public RequestParser request;
    public int tagId;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query = "DELETE FROM tag WHERE tagid = ?";
        this.request = par;
        this.tagId = Integer.parseInt(par.getPath()[1]);

    try {
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, tagId);

        int res = ps.executeUpdate();

        ps.close();

        //System.out.println("tag ["+ tagId +"] was deleted");

        // TODO: prior to the exeption verify if references still active

    } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "DELETE /tags/{gid} - deletes the tag with gid unique identifier.\n";
    }

    @Override
    public Object getData() {
        return this.tagId;
    }
}
