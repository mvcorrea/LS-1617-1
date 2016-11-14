package pt.isel.ls.Formatters.WriterChkLst;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WebFormatter.WebTag;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WriterTask.Task2HTML;

import java.io.IOException;

public class Checklist2HTML {

    public String toHTML(CheckList chk) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("checklist");

        doc.addElem(new WebTag("p").setData(chk.chkName +" - "+ chk.chkIsCompleted+" - "+ chk.chkDueDate +" - "+ chk.chkDesc));
        WebTag list = new WebTag("ul");

        chk.tasks.forEach(x -> {
                list.addContent(new Task2HTML().toHTML(x));
        });

        doc.addElem(list);

        return doc.toString();
    }
}
