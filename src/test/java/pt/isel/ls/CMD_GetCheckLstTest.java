package pt.isel.ls;


import org.junit.*;
import pt.isel.ls.Commands.CMD_GetCheckLst;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.*;
import java.text.ParseException;
import java.util.*;


import static org.junit.Assert.assertTrue;

public class CMD_GetCheckLstTest extends TestHelper {

    @BeforeClass
    public static void setUp() throws Exception {
        setEnv(env);
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

    @Test
    public void CMD_CheckSize() throws AppException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue( lst.size() == 9);
    }

    @Test
    public void CMD_Check1stRow() throws AppException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        //System.out.println(lst.get(0).chkName + " " + lst.get(0).chkDueDate);
        assertTrue(lst.get(0).chkName.compareTo("praiasPT1") == 0
                && lst.get(0).chkDueDate == null
        );
    }

    @Test
    public void CMD_Check2ndRow() throws AppException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        //System.out.println(lst.get(1).chkName + " " + lst.get(1).chkDueDate);
        assertTrue(lst.get(1).chkName.compareTo("museusPT1") == 0
                //&& lst.get(1).chkDueDate.compareTo(str2ts("20150801+1201")) == 0
        );
    }

    @Test
    public void CMD_Check3rdRow() throws AppException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        // System.out.println(lst.get(3).chkName + " " + lst.get(3).chkDueDate);
        assertTrue(lst.get(2).chkName.compareTo("museusLX1@tmpl1") == 0
                && lst.get(2).chkDueDate == null
        );
    }
}