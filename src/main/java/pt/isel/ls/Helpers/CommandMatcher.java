package pt.isel.ls.Helpers;

import pt.isel.ls.Commands.*;
import pt.isel.ls.Debug;
import pt.isel.ls.Exceptions.AppException;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandMatcher {

    // resources list (all commands goes in here)
    private LinkedList<CommandWrapper> resources = new LinkedList<>();

    // instantiating this constructor will buils the resources list
    public CommandMatcher() { addAllCommands(); }

    // fill single command
    public void addCommand(CommandInterface x){ resources.add(new CommandWrapper(x)); }

    // group of all commands
    public void addAllCommands(){  // include all commands here
        // Phase 01
        addCommand(new CMD_ProgramRun());               // EMPTY (on the matchCommand function)
        addCommand(new CMD_PostCheckLst());             // POST /checklists
        addCommand(new CMD_GetCheckLst());              // GET  /checklists
        addCommand(new CMD_PostTask2CheckLst());        // POST /checklists/{cid}/tasks
        addCommand(new CMD_PostChangeStatus());         // POST /checklists/{cid}/tasks/{lid}
        addCommand(new CMD_GetCheckLstDetail());        // GET  /checklists/{cid}
        addCommand(new CMD_PostTemplate());             // POST /templates
        addCommand(new CMD_PostTask2Template());        // POST /templates/{tid}/tasks
        addCommand(new CMD_PostCheckLstFromTemplate()); // POST /templates/{tid}/create
        addCommand(new CMD_GetTemplate());              // GET /templates
        addCommand(new CMD_GetTemplateDetail());        // GET /templates/{tid}
        addCommand(new CMD_GetCheckLstClosed());        // GET /checklists/closed
        addCommand(new CMD_GetCheckLstOpenDueDate());   // GET /checklists/open/sorted/duedate
        addCommand(new CMD_GetCheckLstOpenNumTsks());   // GET /checklists/open/sorted/noftasks
        // Phase 02
        addCommand(new CMD_ProgramExit());              // EXIT /
        addCommand(new CMD_ProgramOpts());              // OPTIONS /
        addCommand(new CMD_PostTag());                  // POST /tags
        addCommand(new CMD_GetTag());                   // GET /tags
        addCommand(new CMD_DeleteTag());                // DELETE /tags/{gid}
        addCommand(new CMD_PostChekLstTag());           // POST /checklists/{cid}/tags
        addCommand(new CMD_DeleteCheckLstTag());        // DELETE /checklists/{cid}/tags/{gid}
        // TESTE
        addCommand(new CMD_TESTE());                    // POST /templates/{tid}⁄checklists/notclosed/tags
        // Phase 03
        addCommand(new CMD_ServerListen());             // LISTEN /
        addCommand(new CMD_GetRoot());                  // GET /
        addCommand(new CMD_GetTagDetail());             // GET /tags/{gid}
        addCommand(new CMD_GetTagChekLst());            // GET /tags/{gid}/checklists

    }

    // list of matched commands given an command line input
    public CommandWrapper matchCommand(String cmdIn) throws AppException {

        LinkedList<CommandWrapper> out = new LinkedList<>();

        resources.stream().forEach( cmd -> {
            Pattern pat = cmd.getCmd().getPattern();
            if(pat == null) return;  // a new command returns null as default
            Matcher m = pat.matcher(cmdIn);
            if(m.matches()) out.add(cmd);
        });

        if(Debug.ON) out.stream().forEach(p -> System.out.println(p.getCmd().getPattern()));  // the resulting list
        if(out.size() == 0) throw new AppException("matchCommand: invalid command error");
        if(out.size() != 1) throw new AppException("matchCommand: ["+ cmdIn +"] command match error");
        return out.getFirst();
    }

    // helper function to list all available commands
    public void lstCommands(){
        resources.stream()
                 .sorted((x,y) -> x.getCmd().toString().compareToIgnoreCase(y.getCmd().toString()))
                 .forEach(p -> System.out.print(p.getCmd().toString()));
    }


}
