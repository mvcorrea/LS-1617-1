package pt.isel.ls.Formatters.WriterChkLst;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;
import java.util.LinkedList;

public class ChecklistCollection2HTML {

    public String toHTML(LinkedList<CheckList> cls) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("checklists");

        WebTag list = new WebTag("ul");
        cls.forEach(chk -> {
            list.addContent(new WebTag("li").setData(chk.chkName +" - "+ chk.chkIsCompleted+" - "+ chk.chkDueDate +" - "+ chk.chkDesc));
        });

        doc.addElem(list);

        return doc.toString();
    }
}
