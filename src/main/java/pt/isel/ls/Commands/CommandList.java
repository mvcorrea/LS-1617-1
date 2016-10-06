package pt.isel.ls.Commands;
import pt.isel.ls.DBConn;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class CommandList {

    // resources list (all commands goes in here)
    public static LinkedList<CommandWrapper> resources = new LinkedList<>();

    // instantiating this constructor will buils the resources list
    public CommandList() { addAllCommands(); }

    // add single command
    public void addCommand(Command_Interface x){ resources.add(new CommandWrapper(x)); }

    // group all commands
    public void addAllCommands(){  // include all commands here
        addCommand(new CMD_PostCheckLst());
        addCommand(new CMD_GetCheckLst());
    }

//    public static void execute(String cmd) throws SQLException { }

    public void lstCommands(){
        resources.stream()
                 .forEach(p -> System.out.println(p.getCmd().getPattern()));
    }

//    public static void main(String[] args) throws IOException { // TODO: goes to test
//        CommandList cmds = new CommandList();
//        cmds.lstCommands();
//    }

}
