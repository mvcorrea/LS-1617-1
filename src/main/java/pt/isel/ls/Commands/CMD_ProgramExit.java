package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandMatcher;
import pt.isel.ls.Helpers.RequestParser;
import pt.isel.ls.ProcessCmd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CMD_ProgramExit implements CommandInterface {
    public static String pattern = "(EXIT /)";
    public RequestParser request;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException {
        this.request = par;
        System.exit(0);
        return null;
    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override
    public String toString() {
        return "EXIT / - ends the application.\n";
    }

    @Override
    public Object getData() {
        return null;
    }
}
