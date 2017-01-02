package pt.isel.ls.Formatters;


import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;

public class WriterRoot {
    public String toHTML() throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("MyApp");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);

        WebTag jumbo =new WebTag("div").setAttr("class", "jumbotron text-center").addContent(new WebTag("h1").setData("LS 1617-1 (Marcelo Correa 26958)"));

        doc.addElem(jumbo);

        return doc.toString();

    }

}
