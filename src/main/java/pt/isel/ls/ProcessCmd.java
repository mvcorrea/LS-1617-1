package pt.isel.ls;

import pt.isel.ls.Commands.CMD_ProgramRun;
import pt.isel.ls.Helpers.CommandMatcher;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.DBConn;
import pt.isel.ls.Helpers.RequestParser;

public class ProcessCmd {

    // inputs:  the request object with fields parsed
    public void doProcess(RequestParser par) throws Exception {

        CommandMatcher commands = new CommandMatcher();
        CommandWrapper cmd = commands.matchCommand(par.matchString());
        cmd.getCmd().process(new DBConn().getDataSource(), par);

    }
}

