package pt.isel.ls.Helpers;

import pt.isel.ls.Commands.*;
import pt.isel.ls.Exceptions.GenericException;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandMatcher {

    // resources list (all commands goes in here)
    private LinkedList<CommandWrapper> resources = new LinkedList<>();

    // instantiating this constructor will buils the resources list
    public CommandMatcher() { addAllCommands(); }

    // add single command
    public void addCommand(CommandInterface x){ resources.add(new CommandWrapper(x)); }

    // group of all commands
    public void addAllCommands(){  // include all commands here
        addCommand(new CMD_ProgramRun());           // EMPTY (on the matchCommand function)
        addCommand(new CMD_PostCheckLst());         // POST /checklists
        addCommand(new CMD_GetCheckLst());          // GET  /checklists
        addCommand(new CMD_PostTask2CheckLst());    // POST /checklists/{cid}/tasks
        addCommand(new CMD_PostChangeStatus());     // POST /checklists/{cid}/tasks/{lid}
        addCommand(new CMD_GetCheckLstDetail());    // GET  /checklists/{cid}
        // addCommand(new CMD_PostTemplates());        // POST /templates
        // POST /templates/{tid}/tasks
        // POST /templates/{tid}/create
        // GET /templates
        // GET /templates/{tid}
        addCommand(new CMD_GetCheckLstClosed());    // GET /checklists/closed
        addCommand(new CMD_GETCheckLstUncompletedDueDate());    // GET /checklists/open/sorted/duedate
        // GET /checklists/open/sorted/noftasks
    }

    // list of matched commands given an command line input
    public CommandWrapper matchCommand(String cmdIn) throws GenericException {

        LinkedList<CommandWrapper> out = new LinkedList<>();

        resources.stream().forEach( cmd -> {
            //  Matcher m = cmd.getCmd().getPattern().matcher(cmdIn);
            // if(m.matches()) out.add(cmd);

            Pattern pat = cmd.getCmd().getPattern();
            if(pat == null) return;  // a new command returns null as default
            Matcher m = pat.matcher(cmdIn);
            if(m.matches()) out.add(cmd);
        });

        //System.out.println(out.size());
        out.stream().forEach(p -> System.out.println(p.getCmd().getPattern()));  // the resulting list
        if(out.size() == 0) throw new GenericException("matchCommand: invalid command error");
        if(out.size() != 1) throw new GenericException("matchCommand: ["+ cmdIn +"] command match error");
        return out.getFirst();
    }

    // helper function to list all available commands
    public void lstCommands(){
        resources.stream()
                 .forEach(p -> System.out.println(p.getCmd().getPattern()));
    }


}
