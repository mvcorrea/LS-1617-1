package pt.isel.ls;


import org.junit.Test;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;
import pt.isel.ls.Formatters.WebFormatter.WebText;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class WebDocumentTest {

    @Test
    public void CheckElemText1(){
        WebTag el = new WebTag("p");
                el.addContent(new WebText().setData("one"));
                el.addContent(new WebText().setData("two"));
                el.addContent(new WebText().setData("three"));
        //System.out.println(el.toString());

        assertTrue(el.toString().compareTo("<p>one two three</p>") == 0);
    }


    @Test
    public void CheckElemText2(){
        WebTag el = new WebTag("p");
                el.addContent(new WebText().setData("one"));
                el.addContent(new WebTag("i").setData("two"));
                el.addContent(new WebText().setData("three"));
        //System.out.println(el.toString());

        assertTrue(el.toString().compareTo("<p>one <i>two</i> three</p>") == 0);
    }

    @Test
    public void CheckElemText3(){
        WebTag el = new WebTag("center").addContent(new WebTag("i").addContent(new WebTag("span").setData("test")));
        //System.out.println(el.toString());
        assertTrue(el.toString().compareTo("<center><i><span>test</span></i></center>") == 0);
    }

    @Test
    public void CheckDocument1() throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("foobar");
        WebTag el = new WebTag("p").setData("data1").setAttr("id", "data1");
        doc.addElem(el);
        //System.out.println(doc.toString());
        assertTrue( doc.toString().contains("<!DOCTYPE html>") &&
                    doc.toString().contains("<title>foobar</title>") &&
                    doc.toString().contains("<p id='data1'>data1</p>")
        );
    }
}
