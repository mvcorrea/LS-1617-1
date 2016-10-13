package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandMatcher;
import pt.isel.ls.Helpers.CommandWrapper;

import java.util.regex.Matcher;

import static org.junit.Assert.assertTrue;


public class CommandMatcherTest {

    public static CommandMatcher commds = new CommandMatcher();

    @Test
    public void CMD_GetCheckLst() throws GenericException {
        String tmp = "GET /checklists";
        CommandWrapper cmd = commds.matchCommand(tmp);
        Matcher m = cmd.getCmd().getPattern().matcher(tmp);
        assertTrue(m.matches());
    }

    @Test
    public void CMD_PostCheckLst() throws GenericException {
        String tmp = "POST /checklists";
        CommandWrapper cmd = commds.matchCommand(tmp);
        Matcher m = cmd.getCmd().getPattern().matcher(tmp);
        assertTrue(m.matches());
    }

    @Test
    public void CMD_PostTask2CheckLst() throws GenericException {
        String tmp = "POST /checklists/200/tasks";
        CommandWrapper cmd = commds.matchCommand(tmp);
        Matcher m = cmd.getCmd().getPattern().matcher(tmp);
        assertTrue(m.matches());
    }

    @Test
    public void CMD_GetCheckLstDetail() throws GenericException {
        String tmp = "GET /checklists/100";
        CommandWrapper cmd = commds.matchCommand(tmp);
        Matcher m = cmd.getCmd().getPattern().matcher(tmp);
        assertTrue(m.matches());
    }






}
