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
    public static Connection conn;
    private static boolean setUpIsDone = false;

    @Before
    public void setUp() throws Exception {
        if (!setUpIsDone) {

            // ONLY WORKS in *NIX Shells
            String[] commd = {"sh", "-c", "mysql -u dbuser -pdbuser < docs/dbTest.sql"};
            Process p = Runtime.getRuntime().exec(commd);
            HashMap<String, String> env  = new HashMap<>();
            env.put("LS_DB_HOST", "127.0.0.1");
            env.put("LS_DB_USER", "dbuser");
            env.put("LS_DB_PASS", "dbuser");
            env.put("LS_DB_NAME", "sampleDB");
            set(env);

            conn = new DBConn().getConnection();
        }
        setUpIsDone = true;
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

//    @Test
//    public void CMD_CheckSize() throws GenericException, SQLException, ParseException {
//        RequestParser rp = new RequestParser("GET /checklists".split(" "));
//        Object cmd = new CMD_GetCheckLst().process(conn, rp);
//        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
//        assertTrue( lst.size() == 2);
//    }
//
//    @Test
//    public void CMD_Check1stRow() throws GenericException, SQLException, ParseException {
//        RequestParser rp = new RequestParser("GET /checklists".split(" "));
//        Object cmd = new CMD_GetCheckLst().process(conn, rp);
//        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
//        assertTrue(lst.get(0).chkName.compareTo("clsample1") == 0
//                && lst.get(0).chkDueDate == null
//        );
//    }
//
//    @Test
//    public void CMD_Check2ndRow() throws GenericException, SQLException, ParseException {
//        RequestParser rp = new RequestParser("GET /checklists".split(" "));
//        Object cmd = new CMD_GetCheckLst().process(conn, rp);
//        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd;
//        assertTrue(lst.get(1).chkName.compareTo("clsample2") == 0
//                && lst.get(1).chkDueDate.compareTo(str2ts("20000101+0001")) == 0
//        );
//    }
}