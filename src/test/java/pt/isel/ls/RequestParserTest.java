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
        RequestParser par = run( new String[]{"GET", "/"} );
        assertTrue(par.getMethod() == "GET" && par.getPath().length == 0 && par.getParams() == null);
    }

    @Test
    public void singleValueRequest3() throws GenericException {
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

    // phase2 requirements using the header field

    @Test
    public void dualValueRequest5() throws GenericException {   // valid method & valid path & valid headers
        RequestParser par = run( new String[]{"GET", "/XPTO/ZZZ", "accept:text/plain"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && par.getHeaders().get("accept").equals("text/plain"));
    }

    @Test
    public void dualValueRequest6() throws GenericException {   // valid method & valid path & valid headers
        RequestParser par = run( new String[]{"GET", "/XPTO/ZZZ", "accept:text/html|file-name:xpto.html"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && par.getHeaders().get("accept").equals("text/html") &&
                par.getHeaders().get("file-name").equals("xpto.html"));
    }

    @Test
    public void dualValueRequest7() throws GenericException {   // valid method & valid path & valid headers
        RequestParser par = run( new String[]{"GET", "/XPTO/ZZZ", "accept:application/json|file-name:xpto.json"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && par.getHeaders().get("accept").equals("application/json") &&
                par.getHeaders().get("file-name").equals("xpto.json"));
    }

    @Test
    public void dualValueRequest8() throws GenericException {   // valid method & valid path & valid headers
        RequestParser par = run( new String[]{"GET", "/XPTO/ZZZ", "accept:application/json|file-name:xpto.json", "y=4"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && par.getHeaders().get("accept").equals("application/json") &&
                par.getHeaders().get("file-name").equals("xpto.json") && Integer.parseInt(par.getParams().get("y")) == 4 );
    }

    @Test
    public void dualValueRequest9() throws GenericException {   // valid method & valid path & valid headers
        RequestParser par = run( new String[]{"GET", "/XPTO/ZZZ", "accept:application/json|file-name:xpto.json", "y=4&x=3"} );
        assertTrue(par.getMethod() == "GET" && par.getPath()[0].equals("XPTO") &&
                par.getPath()[1].equals("ZZZ") && par.getHeaders().get("accept").equals("application/json") &&
                par.getHeaders().get("file-name").equals("xpto.json") && Integer.parseInt(par.getParams().get("y")) == 4 &&
                Integer.parseInt(par.getParams().get("x")) == 3);
    }


    @Test
    public void dualValueExit() throws GenericException {   // EXIT Test
        RequestParser par = run( new String[]{"EXIT", "/"} );
        assertTrue(par.getMethod() == "EXIT" && par.getPath().length == 0);
    }

    @Test
    public void dualValueOptions() throws GenericException {   // EXIT Test
        RequestParser par = run( new String[]{"OPTIONS", "/"} );
        assertTrue(par.getMethod() == "OPTIONS" && par.getPath().length == 0);
    }


    // check exceptions

    @Test (expected = GenericException.class)
    public void dualValueRequestErr1() throws GenericException {    // invalid method with slash
        RequestParser par = run( new String[]{"XXX", "/"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr2() throws GenericException {   // invalid method with path
        RequestParser par = run( new String[]{"XXX", "/XPTO"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr3() throws GenericException {   // valid method invalid path
        RequestParser par = run( new String[]{"GET", "XPTO"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr4() throws GenericException {   // with invalid params
        RequestParser par = run( new String[]{"GET", "/XPTO", "xxx"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr5() throws GenericException {   // with invalid params & headers
        RequestParser par = run( new String[]{"GET", "/XPTO", "xxx", "yyy"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr6() throws GenericException {   // with invalid params (separator included)
        RequestParser par = run( new String[]{"GET", "/XPTO", "xxx="} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr7() throws GenericException {   // with invalid params (2 separator included)
        RequestParser par = run( new String[]{"GET", "/XPTO", "&xxx="} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr8() throws GenericException {   // with invalid params (2 separator included)
        RequestParser par = run( new String[]{"GET", "/XPTO", "&x=xx"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr9() throws GenericException {   // with invalid headers (separator included)
        RequestParser par = run( new String[]{"GET", "/XPTO", "yy|zz"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr10() throws GenericException {   // with invalid headers (2 separator included)
        RequestParser par = run( new String[]{"GET", "/XPTO", "yy|z:z"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr11() throws GenericException {   // with invalid headers (2 separator included)
        RequestParser par = run( new String[]{"GET", "/XPTO", "y&y|z:z=3"} );
    }

    @Test (expected = GenericException.class)
    public void dualValueRequestErr12() throws GenericException {   // reverse params & headers
        RequestParser par = run( new String[]{"GET", "/XPTO", "y=4", "accept:text/plain"} );
    }


}
