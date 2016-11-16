package pt.isel.ls;

import pt.isel.ls.Containers.ContainerInterface;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.*;

import java.io.*;
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
            String outData = out.format(rep);
            String filename = par.getHeaders().get("file-name");

            if(filename == null){       // check if data need to be printed to file
                System.out.println(outData);
            } else {
                if (outData == null) throw new AppException("outData: no data returned");
                this.toFile(filename, outData);
            }

        } else { // the remain commands returns an integer

            if(!par.getMethod().equals("OPTIONS")){ // avoid empty data
                CommandWrapper cw = (CommandWrapper) rep;
                int id = (int) cw.getCmd().getData();
                System.out.println("created/updated/deleted id: "+ id);
            }

        }

        conn.close();
    }

    private void toFile(String filename, String output) throws IOException, AppException {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(output);
            out.close();

        } catch (FileNotFoundException e){  throw new AppException("toFile: "+ e.getMessage()); }
    }
}

