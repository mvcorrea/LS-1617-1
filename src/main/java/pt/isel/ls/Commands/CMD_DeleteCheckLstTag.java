package pt.isel.ls.Commands;


import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CMD_DeleteCheckLstTag implements CommandInterface {
    public static String pattern = "(POST /checklists/\\d+/tags/\\d+)";
    public int cid, gid;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, GenericException {
        String query = "DELETE FROM chk2tag WHERE xchkId = ? AND xtagId = ?";
        this.cid = Integer.parseInt(par.getPath()[1]);
        this.gid = Integer.parseInt(par.getPath()[3]);

        try {

        }

        return null;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "DELETE /checklists/{cid}/tags/{gid} - deletes the association between the cid checklist and the gid tag.\n";
    }


}
