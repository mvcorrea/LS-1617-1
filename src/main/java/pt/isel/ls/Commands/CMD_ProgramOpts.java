package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandMatcher;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;


public class CMD_ProgramOpts implements CommandInterface {
    public static String pattern = "(OPTIONS /)";

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public Object process(Connection con, RequestParser p) throws SQLException {

        CommandMatcher commands = new CommandMatcher();
        commands.lstCommands();
        return null;
    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override
    public String toString() {
        return "OPTIONS / - presents a list of available commands and their characteristics.\n";
    }
}
