package pt.isel.ls;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Commands.CMD_GetCheckLstOpenDueDate;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;


public class CMD_GetCheckLstOpenDueDateTest_ extends TestHelper{
    @BeforeClass
    public static void setUp() throws Exception {
        setEnv(env);
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

    @Test
    public void CMD_CheckNumber() throws AppException, SQLException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, org.json.simple.parser.ParseException {
        RequestParser rp = new RequestParser("GET /checklists/closed".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLstOpenDueDate().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue(lst.size() == 4);
    }

    @Test
    public void CMD_CheckNames() throws AppException, SQLException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, org.json.simple.parser.ParseException {
        RequestParser rp = new RequestParser("GET /checklists/closed".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLstOpenDueDate().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        //System.out.println(lst.get(0).chkName +" "+  lst.get(1).chkName);
        assertTrue(lst.get(0).chkName.equals("praiasLX1@tmpl2") && lst.get(1).chkName.equals("restaurantesLX1@tmpl3"));
    }


}
