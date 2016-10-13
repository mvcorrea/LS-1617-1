package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

// command sample: POST /checklists/1/tasks name=task1&description=my+task+descr&dueDate=20160920+1400

public class CMD_PostCheckLst implements CommandInterface {
    public static String pattern = "(POST /checklists)";

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
        return "POST /checklists - creates a new checklist.\n";
    }
}