package pt.isel.ls;


import org.junit.*;
import pt.isel.ls.Commands.CMD_GetCheckLst;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.DBConn;
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
    public void CMD_CheckSize() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        Object cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
        assertTrue( lst.size() == 3);
    }

    @Test
    public void CMD_Check1stRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        Object cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
        assertTrue(lst.get(0).chkName.compareTo("clsample1") == 0
                && lst.get(0).chkDueDate == null
        );
    }

    @Test
    public void CMD_Check2ndRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        Object cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
        assertTrue(lst.get(1).chkName.compareTo("clsample2") == 0
                && lst.get(1).chkDueDate.compareTo(str2ts("20000101+0001")) == 0
        );
    }

    @Test
    public void CMD_Check3rdRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        Object cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
        assertTrue(lst.get(2).chkName.compareTo("clsample3") == 0
                && lst.get(2).chkDueDate == null
        );
    }
}