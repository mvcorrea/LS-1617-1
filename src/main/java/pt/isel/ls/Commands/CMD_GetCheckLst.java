package pt.isel.ls.Commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

// command sample: GET /checklists

public class CMD_GetCheckLst  implements Command_Interface {
    public static String pattern = "(GET /checklists)";

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con) throws SQLException {

    }

    @Override public String toString(){
        return "GET /checklists - returns a list with all the checklists.\n";
    }
}
