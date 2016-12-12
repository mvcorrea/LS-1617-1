package pt.isel.ls.Formatters.WriterChkLst;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ChecklistCollection2HTML {

    public String getDueDate(Timestamp ts){
        if(ts != null) return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ts);
        else return "-";
    }

    public String toHTML(LinkedList<CheckList> cls) throws IOException {

        WebDocument doc = new WebDocument();
        doc.setTitle("checklists");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","/checklists/closed").setData("Closed")));
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","/checklists/open/sorted/duedate").setData("Open/DueDate")));
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","/checklists/open/sorted/noftasks").setData("Open/NumTasks")));
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);


        // main body
        WebTag main = new WebTag("div").setAttr("class", "container");

        WebTag table = new WebTag("table").setAttr("class", "table table-striped").nl();

        WebTag thead =  new WebTag("thead").nl();
        thead.addContent(new WebTag("th").setData("Name"));
        thead.addContent(new WebTag("th").setData("Description"));
        thead.addContent(new WebTag("th").setData("Due Date"));
        thead.addContent(new WebTag("th").setData("Completed"));
        thead.addContent(new WebTag("th").setData("Tags"));
        table.addContent(thead);

        WebTag tbody = new WebTag("tbody").nl();
        cls.forEach(chk -> {
            String tags = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));
            WebTag row = new WebTag("tr").nl();
            row.addContent(new WebTag("td").addContent(new WebTag("a").setAttr("href","/checklists/"+chk.chkId).setData(chk.chkName)));
            //row.addContent(new WebTag("td").setData(chk.chkName));
            row.addContent(new WebTag("td").setData(chk.chkDesc));
            row.addContent(new WebTag("td").setData(getDueDate(chk.chkDueDate)));
            row.addContent(new WebTag("td").setData(chk.chkIsCompleted?"True":"False"));
            row.addContent(new WebTag("td").setData(tags));
            tbody.addContent(row);
        });

        table.addContent(tbody);

        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("CheckList(s)"));
        main.addContent(table);


        doc.addElem(main);




//
//
//        WebTag list = new WebTag("ul");
//        cls.forEach(chk -> {
//            list.addContent(new WebTag("li").setData(chk.chkName +" - "+ chk.chkIsCompleted+" - "+ chk.chkDueDate +" - "+ chk.chkDesc));
//        });

//        doc.addElem(list);

        return doc.toString();
    }
}
