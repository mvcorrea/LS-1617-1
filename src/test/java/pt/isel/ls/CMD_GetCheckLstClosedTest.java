package pt.isel.ls;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Commands.CMD_GetCheckLstClosed;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

public class CMD_GetCheckLstClosedTest extends TestHelper {
    @BeforeClass
    public static void setUp() throws Exception {
        setEnv(env);
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

    @Test
    public void CMD_CheckCompleted() throws AppException, SQLException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, org.json.simple.parser.ParseException {
        RequestParser rp = new RequestParser("GET /checklists/closed".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLstClosed().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue(lst.size() == 5);
    }

    @Test
    public void CMD_CheckNames() throws AppException, SQLException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, org.json.simple.parser.ParseException {
        RequestParser rp = new RequestParser("GET /checklists/closed".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLstClosed().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue(lst.get(0).chkName.equals("praiasPT1") && lst.get(1).chkName.equals("museusPT1") && lst.get(2).chkName.equals("museusLX1@tmpl1"));
    }

}
