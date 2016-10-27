package pt.isel.ls;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Commands.CMD_GetTag;
import pt.isel.ls.Commands.CMD_PostTag;
import pt.isel.ls.Commands.CMD_DeleteTag;
import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void CMD_Get1stRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /tags".split(" "));
        Object cmd = new CMD_GetTag().process(conn, rp);
        LinkedList<Tag> lst = (LinkedList<Tag>) cmd;
        assertTrue(lst.get(0).tagName.compareTo("work") == 0
                && lst.get(0).tagColor.compareTo("blue") == 0
        );
    }

    @Test
    public void CMD_Get2ndRow() throws GenericException, SQLException, ParseException {
        RequestParser rp = new RequestParser("GET /tags".split(" "));
        Object cmd = new CMD_GetTag().process(conn, rp);
        LinkedList<Tag> lst = (LinkedList<Tag>) cmd;
        assertTrue(lst.get(1).tagName.compareTo("vacation") == 0
                && lst.get(1).tagColor.compareTo("green") == 0
        );
    }

    @Test
    public void CMD_PostTag() throws GenericException, SQLException, ParseException {
        RequestParser rp1 = new RequestParser("POST /tag name=coisos&color=red".split(" "));
        int id = (int) new CMD_PostTag().process(conn, rp1);

        RequestParser rp2 = new RequestParser("GET /tags".split(" "));
        Object cmd = new CMD_GetTag().process(conn, rp2);
        LinkedList<Tag> lst = (LinkedList<Tag>) cmd;
        List<Tag> t = lst.stream().filter(x -> x.tagId == id).collect(Collectors.toList());
        assertTrue(t.get(0).tagName.compareTo("coisos") == 0
                && t.get(0).tagColor.compareTo("red") == 0
        );
    }

    @Test
    public void CMD_DeleteTag1() throws GenericException, SQLException, ParseException {
        RequestParser rp1 = new RequestParser("POST /tag name=cenas&color=pink".split(" "));
        int id = (int) new CMD_PostTag().process(conn, rp1);  // included id = 4 but list position = 3

        RequestParser rp2 = new RequestParser("GET /tags".split(" "));
        Object cmd1 = new CMD_GetTag().process(conn, rp2);
        LinkedList<Tag> lst1 = (LinkedList<Tag>) cmd1;
        List<Tag> t1 = lst1.stream().filter(x -> x.tagId == id).collect(Collectors.toList());

        RequestParser rp3 = new RequestParser(("DELETE /tags/"+t1.get(0).tagId).split(" "));
        Object cmd2 = new CMD_DeleteTag().process(conn, rp3);

        RequestParser rp4 = new RequestParser("GET /tags".split(" "));
        Object cmd3 = new CMD_GetTag().process(conn, rp4);
        LinkedList<Tag> lst2 = (LinkedList<Tag>) cmd3;
        List<Tag> t2 = lst2.stream().filter(x -> x.tagId == id).collect(Collectors.toList());

        assertTrue(t2.size() == 0);
    }

    @Test  (expected = GenericException.class)
    public void CMD_DeleteTag2() throws GenericException, SQLException, ParseException { // unable to delete refs active
        RequestParser rp2 = new RequestParser("DELETE /tags/1".split(" "));
        Object cmd1 = new CMD_DeleteTag().process(conn, rp2);
    }

}
