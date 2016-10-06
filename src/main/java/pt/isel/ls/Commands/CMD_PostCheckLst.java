package pt.isel.ls.Commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

// command sample: POST /checklists name=chklist1&description=my+chklst+descr&dueDate=20160920+1400

public class CMD_PostCheckLst implements Command_Interface {
    public static String pattern = "(POST /checklists)";

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con) throws SQLException {

    }

    @Override public String toString(){
        return "POST /checklists - creates a new checklist.\n";
    }
}
