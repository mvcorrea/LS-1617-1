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

public class CMD_PostChekLstTag implements CommandInterface {
    public static String pattern = "(POST /checklists/\\d+/tags)";
    public RequestParser request;
    public int cid, assoc;

    @Override
    public RequestParser getRequest() {
        return request;
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query = "INSERT INTO chk2tag (xtagId, xchkId) VALUES (?, ?)";
        this.request = par;
        this.cid = Integer.parseInt(par.getPath()[1]);

        try {

            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Integer.valueOf(par.getParams().get("gid")));
            ps.setInt(2, this.cid);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            this.assoc = rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e){ throw new DBException( e.getMessage()); }

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "POST /checklists/{cid}/tags - associate a tag to the cid.\n";
    }

    @Override
    public Object getData() {
        return assoc;
    }
}