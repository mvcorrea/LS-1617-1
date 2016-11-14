package pt.isel.ls;

import pt.isel.ls.Containers.ContainerInterface;
import pt.isel.ls.Helpers.*;

import java.sql.Connection;

public class ProcessCmd {

    private CommandMatcher commands = new CommandMatcher();

    // inputs:  the request object with fields parsed
    public void doProcess(RequestParser par) throws Exception {

        CommandWrapper cmd = commands.matchCommand(par.matchString());
        Connection conn = new DBConn().getConnection();
        Object rep = cmd.getCmd().process(conn, par);

        if(par.getMethod().equals("GET")){  // only get outputs data
            OutputFormatter out = new OutputFormatter();
            System.out.println(out.format(rep));
        } else { // the remain commands returns an integer
            CommandWrapper cw = (CommandWrapper) rep;
            int id = (int) cw.getCmd().getData();
            System.out.println("created/updated/deleted id: "+ id);
        }

        conn.close();
    }
}

