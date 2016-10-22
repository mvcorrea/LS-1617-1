package pt.isel.ls;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Commands.CMD_GetCheckLst;
import pt.isel.ls.Commands.CMD_GetCheckLstDetail;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.DBConn;
import pt.isel.ls.Helpers.RequestParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class CMD_GetCheckLstDetailTest extends TestHelper {

//    public static Connection conn;
//    private static boolean setUpIsDone = false;

    @BeforeClass
    public static void setUp() throws Exception {
        //TestHelper ts = new TestHelper();
        HashMap<String, String> env = new HashMap<>();
        env.put("LS_DB_HOST", "127.0.0.1");
        env.put("LS_DB_USER", "dbuser");
        env.put("LS_DB_PASS", "dbuser");
        env.put("LS_DB_NAME", "sampleDB");
        set(env);




//        TestHelper ts = new TestHelper();
//        ts.setEnv();

//        if (!setUpIsDone) {
//            // ONLY WORKS in *NIX Shells
//            String[] commd = {"sh", "-c", "mysql -u dbuser -pdbuser < docs/dbsample.sql"};
//            Process p = Runtime.getRuntime().exec(commd);
//
//            HashMap<String, String> env = new HashMap<>();
//            env.put("LS_DB_HOST", "127.0.0.1");
//            env.put("LS_DB_USER", "dbuser");
//            env.put("LS_DB_PASS", "dbuser");
//            env.put("LS_DB_NAME", "sampleDB");
//            set(env);
//
//            conn = new DBConn().getConnection();
//        }
//        setUpIsDone = true;
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

    @Test
    public void CMD_Check1stRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists/1".split(" "));
        Object cmd = new CMD_GetCheckLstDetail().process(conn, rp);
        CheckList ckl = (CheckList) cmd;
        assertTrue(ckl.chkName.compareTo("clsample1") == 0
                && ckl.chkDueDate == null
        );
    }

    @Test
    public void CMD_Check2ndRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists/2".split(" "));
        Object cmd = new CMD_GetCheckLstDetail().process(conn, rp);
        CheckList ckl = (CheckList) cmd;
        assertTrue(ckl.chkName.compareTo("clsample2") == 0
                && ckl.chkDueDate.compareTo(str2ts("20000101+0001")) == 0
        );
    }

}
