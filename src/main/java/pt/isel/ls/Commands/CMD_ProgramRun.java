package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.*;
import pt.isel.ls.ProcessCmd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CMD_ProgramRun implements CommandInterface {
    public static String pattern = "";
    public RequestParser request;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    // TODO: verify All Exception && USE Transactions (setAutoCommit) !!!

    @Override
    public Object process(Connection con, RequestParser par0) throws SQLException { // see if parsedcmd goes here
        Scanner s = new Scanner(System.in);
        CommandMatcher commands = new CommandMatcher();
        this.request = par0;

        while(true){
            System.out.print("?> ");
            String line = s.nextLine();
            if(line.length() == 0) continue;
            String[] args =line.split(" ");

            try {
                RequestParser par = new RequestParser(args);
                new ProcessCmd().doProcess(par);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override
    public String toString() {
        return "\n";
    }

    @Override
    public Object getData() {
        return null;
    }
}
