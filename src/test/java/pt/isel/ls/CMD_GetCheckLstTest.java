package pt.isel.ls;


import org.junit.*;
import pt.isel.ls.Commands.CMD_GetCheckLst;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.DBConn;
import pt.isel.ls.Helpers.RequestParser;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static org.junit.Assert.assertTrue;

public class CMD_GetCheckLstTest extends TestHelper {

    public static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {

//        WORKING !!!
//        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//        final String DB_URL = "jdbc:mysql://localhost/";
//
//        final String USER = "dbuser";
//        final String PASS = "dbuser";
//
//        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        Statement stmt  = conn.createStatement();
//
//        stmt.executeUpdate("DROP DATABASE IF EXISTS sampleDB");
//        stmt.executeUpdate("CREATE DATABASE sampleDB");

        // ONLY WORKS in *NIX Shells
        String[] commd = {"sh", "-c", "mysql -u dbuser -pdbuser < docs/dbsample.sql"};
        //System.out.println(String.join(" ", commd));
        Process p = Runtime.getRuntime().exec(commd);

        HashMap<String, String> env  = new HashMap<>();
        env.put("LS_DB_HOST", "127.0.0.1");
        env.put("LS_DB_USER", "dbuser");
        env.put("LS_DB_PASS", "dbuser");
        env.put("LS_DB_NAME", "sampleDB");
        set(env);

        conn = new DBConn().getDataSource();
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

    @Test
    public void CMD_CheckSize() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue( lst.size() == 2);
    }

    @Test
    public void CMD_Check1stRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue(lst.get(0).chkName.compareTo("clsample1") == 0
                && lst.get(0).chkDueDate == null
        );
    }

    @Test
    public void CMD_Check2ndRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /checklists".split(" "));
        CommandWrapper cmd = new CMD_GetCheckLst().process(conn, rp);
        LinkedList<CheckList> lst = (LinkedList<CheckList>) cmd.getCmd().getData();
        assertTrue(lst.get(1).chkName.compareTo("clsample2") == 0
                && lst.get(1).chkDueDate.compareTo(str2ts("20000101+0001")) == 0
        );
    }
}