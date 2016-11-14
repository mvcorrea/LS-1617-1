package pt.isel.ls;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Commands.CMD_GetCheckLstDetail;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;

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
    public void CMD_Check1stRow() throws AppException, SQLException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RequestParser rp = new RequestParser("GET /checklists/1".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLstDetail().process(conn, rp);
        CheckList ckl = (CheckList) cmd.getCmd().getData();
        assertTrue(ckl.chkName.compareTo("praiasPT1") == 0
                && ckl.chkDueDate == null
        );
    }

    @Test
    public void CMD_Check2ndRow() throws AppException, SQLException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RequestParser rp = new RequestParser("GET /checklists/2".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetCheckLstDetail().process(conn, rp);
        CheckList ckl = (CheckList) cmd.getCmd().getData();
        assertTrue(ckl.chkName.compareTo("museusPT1") == 0
        );
    }

}
