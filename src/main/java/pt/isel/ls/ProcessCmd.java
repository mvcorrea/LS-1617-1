package pt.isel.ls;

import pt.isel.ls.Helpers.CommandMatcher;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.DBConn;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;

public class ProcessCmd {

    private CommandMatcher commands = new CommandMatcher();

    // inputs:  the request object with fields parsed
    public void doProcess(RequestParser par) throws Exception {

        CommandWrapper cmd = commands.matchCommand(par.matchString());
        Connection conn = new DBConn().getConnection();
        cmd.getCmd().process(conn, par);
        conn.close();

    }
}

