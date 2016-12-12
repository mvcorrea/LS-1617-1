package pt.isel.ls.Formatters.WriterTag;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;
import java.util.LinkedList;

public class Tag2HTML {
    public String toHTML(LinkedList<Tag> tgs) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("Tags");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);


        WebTag main = new WebTag("div").setAttr("class", "container");

        WebTag tableT = new WebTag("table").setAttr("class", "table table-striped").nl();
        WebTag theadT =  new WebTag("thead").nl();
        theadT.addContent(new WebTag("th").setData("Name"));
        theadT.addContent(new WebTag("th").setData("Color"));
        tableT.addContent(theadT);

        WebTag tbodyT = new WebTag("tbody").nl();
        tgs.forEach(tag -> {
            // String tags = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));
            WebTag row = new WebTag("tr").nl();

            // dead end
            row.addContent(new WebTag("td").addContent(new WebTag("a").setAttr("href","tags/"+tag.tagId).setData(tag.tagName)));
            //row.addContent(new WebTag("td").setData(tag.tagName));
            row.addContent(new WebTag("td").setData(tag.tagColor));
            tbodyT.addContent(row);
        });
        tableT.addContent(tbodyT);
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Tags"));
        main.addContent(tableT);
        doc.addElem(main);




//        WebTag list = new WebTag("ul");
//        tgs.forEach(tg -> {
//            list.addContent(new WebTag("li").setData(tg.tagName +" - "+ tg.tagColor));
//        });
//
//        doc.addElem(list);

        return doc.toString();
    }
}
