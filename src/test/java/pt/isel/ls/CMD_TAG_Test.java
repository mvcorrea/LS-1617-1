package pt.isel.ls;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Commands.CMD_GetTag;
import pt.isel.ls.Commands.CMD_PostTag;
import pt.isel.ls.Commands.CMD_DeleteTag;
import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CMD_TAG_Test extends TestHelper {
    private int size = 2; // initial tags number of rows

    @BeforeClass
    public static void setUp() throws Exception {
        setEnv(env);
    }

    @AfterClass
    public static void tidyUp() throws Exception {
        conn.close();
    }

    @Test
    public void CMD_Get1stRow() throws AppException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /tags".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetTag().process(conn, rp);
        LinkedList<Tag> lst = (LinkedList<Tag>) cmd.getCmd().getData();
        assertTrue(lst.get(0).tagName.compareTo("work") == 0
                && lst.get(0).tagColor.compareTo("blue") == 0
        );
    }

    @Test
    public void CMD_Get2ndRow() throws AppException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /tags".split(" "));
        CommandWrapper cmd = (CommandWrapper) new CMD_GetTag().process(conn, rp);
        LinkedList<Tag> lst = (LinkedList<Tag>) cmd.getCmd().getData();
        assertTrue(lst.get(1).tagName.compareTo("vacation") == 0
                && lst.get(1).tagColor.compareTo("green") == 0
        );
    }

    @Test
    public void CMD_PostTag() throws AppException, SQLException, ParseException {
        RequestParser rp1 = new RequestParser("POST /tag name=coisos&color=red".split(" "));
        CommandWrapper cmd1 = (CommandWrapper) new CMD_PostTag().process(conn, rp1);
        int id = (int) cmd1.getCmd().getData();

        RequestParser rp2 = new RequestParser("GET /tags".split(" "));
        CommandWrapper cmd2 = (CommandWrapper) new CMD_GetTag().process(conn, rp2);
        LinkedList<Tag> lst = (LinkedList<Tag>) cmd2.getCmd().getData();
        List<Tag> t = lst.stream().filter(x -> x.tagId == id).collect(Collectors.toList());

        assertTrue(t.get(0).tagName.compareTo("coisos") == 0
                && t.get(0).tagColor.compareTo("red") == 0
        );

        RequestParser rp3 = new RequestParser(("DELETE /tags/"+id).split(" "));
        CommandWrapper cmd3 = (CommandWrapper) new CMD_DeleteTag().process(conn, rp3);
    }

    @Test
    public void CMD_DeleteTag1() throws AppException, SQLException, ParseException {
        RequestParser rp1 = new RequestParser("POST /tag name=cenas&color=pink".split(" "));
        CommandWrapper cmd1 = (CommandWrapper) new CMD_PostTag().process(conn, rp1);
        int id = (int) cmd1.getCmd().getData();  // included id = 4 but list position = 3

        RequestParser rp2 = new RequestParser("GET /tags".split(" "));
        CommandWrapper cmd2 = (CommandWrapper) new CMD_GetTag().process(conn, rp2);
        LinkedList<Tag> lst1 = (LinkedList<Tag>) cmd2.getCmd().getData();
        List<Tag> t1 = lst1.stream().filter(x -> x.tagId == id).collect(Collectors.toList());

        RequestParser rp3 = new RequestParser(("DELETE /tags/"+t1.get(0).tagId).split(" "));
        CommandWrapper cmd3 = (CommandWrapper) new CMD_DeleteTag().process(conn, rp3);

        RequestParser rp4 = new RequestParser("GET /tags".split(" "));
        CommandWrapper cmd4 = (CommandWrapper) new CMD_GetTag().process(conn, rp4);
        LinkedList<Tag> lst2 = (LinkedList<Tag>) cmd4.getCmd().getData();
        List<Tag> t2 = lst2.stream().filter(x -> x.tagId == id).collect(Collectors.toList());

        assertTrue(t2.size() == 0);
    }

    @Test  (expected = AppException.class)
    public void CMD_DeleteTag2() throws AppException, SQLException, ParseException { // unable to delete refs active
        RequestParser rp2 = new RequestParser("DELETE /tags/1".split(" "));
        Object cmd1 = new CMD_DeleteTag().process(conn, rp2);
    }

}
