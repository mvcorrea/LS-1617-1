package pt.isel.ls.Formatters.WriterTag;


import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;

public class Tag2HTML {
    public String toHTML(Tag tag) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("Tag");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);

        // tag
        WebTag tableC = new WebTag("table").setAttr("class", "table table-striped").nl();
        WebTag theadC =  new WebTag("thead").nl();
        theadC.addContent(new WebTag("th").setData("Name"));
        theadC.addContent(new WebTag("th").setData("Color"));
        tableC.addContent(theadC);

        WebTag tbodyC = new WebTag("tbody").nl();

        WebTag rowC = new WebTag("tr").nl();
        rowC.addContent(new WebTag("td").addContent(new WebTag("a")
                .setAttr("href","/tags/"+tag.tagId+"/checklists").setData(tag.tagName)));
        //rowC.addContent(new WebTag("td").setData(tag.tagName));
        rowC.addContent(new WebTag("td").setData(tag.tagColor));


        // checklists with that tag





        tbodyC.addContent(rowC);
        tableC.addContent(tbodyC);


        WebTag main = new WebTag("div").setAttr("class", "container");
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Tag"));
        main.addContent(tableC);
        doc.addElem(main);


        return doc.toString();
    }

}
