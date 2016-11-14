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

        WebTag list = new WebTag("ul");
        tgs.forEach(tg -> {
            list.addContent(new WebTag("li").setData(tg.tagName +" - "+ tg.tagColor));
        });

        doc.addElem(list);

        return doc.toString();
    }
}
