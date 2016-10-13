package pt.isel.ls;


import org.junit.Test;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.RequestParser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestParserTest {

    private static RequestParser run(String[] input) throws GenericException {
        return new RequestParser(input);
    }

    @Test
    public void emptyRequest() throws GenericException { // go interactive
        RequestParser par = run( new String[]{""} );
        assertTrue(par.getMethod() == "" && par.getPath() == null && par.getParams() == null);
    }

    @Test
    public void singleValueRequest1() throws GenericException {
        RequestParser par = run( new String[]{"GET"} );
        assertTrue(par.getMethod() == "" && par.getPath() == null && par.getParams() == null);
    }

    @Test
    public void singleValueRequest2() throws GenericException {
        RequestParser par = run( new String[]{"XPTO"} );
        assertTrue(par.getMethod() == "" && par.getPath() == null && par.getParams() == null);
    }

    @Test
    public void dualValueRequest1() throws GenericException {   // valid method & valid path
        RequestParser par = run( new String[]{"GET", "/XPTO"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") && par.getParams() == null);
    }

    @Test
    public void dualValueRequest2() throws GenericException {   // valid method & valid path
        RequestParser par = run( new String[]{"POST", "/XPTO/ZZZ"} );
        assertTrue(par.getMethod() == "POST" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && par.getParams() == null);
    }


    @Test
    public void dualValueRequest3() throws GenericException {   // valid method & valid path
        RequestParser par = run( new String[]{"GET", "/XPTO", "x=3"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") &&
                Integer.parseInt(par.getParams().get("x")) == 3);
    }

    @Test
    public void dualValueRequest4() throws GenericException {   // valid method & valid path
        RequestParser par = run( new String[]{"POST", "/XPTO/ZZZ", "x=3&y=4"} );
        assertTrue(par.getMethod() == "POST" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && Integer.parseInt(par.getParams().get("x")) == 3 &&
                Integer.parseInt(par.getParams().get("y")) == 4 );
    }

    // check exceptions


    @Test (expected = GenericException.class)
    public void dualValueRequestErr1() throws GenericException {   // valid method
        RequestParser par = run( new String[]{"XXX", "/XPTO"} );
        assertFalse(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") && par.getParams() == null);
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr2() throws GenericException {   // valid path
        RequestParser par = run( new String[]{"GET", "XPTO"} );
        assertFalse(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") && par.getParams() == null);
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr3() throws GenericException {   // with valid params
        RequestParser par = run( new String[]{"GET", "/XPTO", "xxx"} );
        assertFalse(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") && par.getParams() == null);
    }

}
