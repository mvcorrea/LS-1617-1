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

    @BeforeClass
    public static void setUp() throws Exception {
        setEnv(env);
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
