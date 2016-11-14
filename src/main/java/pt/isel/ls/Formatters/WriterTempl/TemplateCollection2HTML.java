package pt.isel.ls.Formatters.WriterTempl;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;
import java.util.LinkedList;

public class TemplateCollection2HTML {
    public String toHTML(LinkedList<Template> tps) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("templates");

        WebTag list = new WebTag("ul");
        tps.forEach(tp -> {
            list.addContent(new WebTag("li").setData(tp.temName +" - "+ tp.temDesc));
        });

        doc.addElem(list);

        return doc.toString();
    }

}
