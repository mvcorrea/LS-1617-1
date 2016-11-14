package pt.isel.ls.Formatters.WriterTempl;

import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;
import pt.isel.ls.Formatters.WriterTask.Task2HTML;
import pt.isel.ls.Formatters.WriterTask.Task2TEXT;

import java.io.IOException;
import java.util.StringJoiner;

public class Template2HTML {

    public String toHTML(Template chk) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("template");

        StringJoiner joiner = new StringJoiner(", ");
        if(chk.checklists.size() > 0){
            chk.checklists.forEach( x -> {
                joiner.add(String.valueOf(x.chkId));
            });
        }

        doc.addElem(new WebTag("p").setData(chk.temName +" - "+  chk.temDesc +" - ["+ joiner.toString() +"]"));
        WebTag list = new WebTag("ul");



        if(chk.tasks.size() > 0 ) {  // detail (checklist with tasks)
            chk.tasks.forEach(x -> {
                list.addContent(new Task2HTML().toHTML(x));
            });
        }

        doc.addElem(list);

        return doc.toString();
    }
}
