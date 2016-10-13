package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

// command sample: POST /checklists name=chklist1&description=my+chklst+descr&dueDate=20160920+1400

public class CMD_PostTask2CheckLst implements CommandInterface {
    public static String pattern = "(POST /checklists/\\d+/tasks)";

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con, RequestParser par) throws SQLException {

    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override public String toString(){
        return "POST /checklists/{cid}/tasks - creates a new task in the checklist 'cid'.\n";
    }
}
