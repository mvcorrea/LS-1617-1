package pt.isel.ls.Commands;


import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CMD_PostChekLstTag implements CommandInterface{
    public static String pattern = "(POST /checklists/\\d+/tags)";
    public int cid;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, GenericException {
        String query = "INSERT INTO chk2tag (xtagId, xchkId) VALUES (?, ?)";
        this.cid = Integer.parseInt(par.getPath()[1]);


        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.valueOf(par.getParams().get("gid")));
            ps.setInt(2, this.cid);
            ps.execute();
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
        return "POST /checklists/{cid}/tags - associate a tag to the cid.\n";
    }
}
