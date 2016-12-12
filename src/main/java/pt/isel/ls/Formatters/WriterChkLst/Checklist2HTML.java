package pt.isel.ls.Formatters.WriterChkLst;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WebFormatter.WebTag;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WriterTask.Task2HTML;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Checklist2HTML {

    public String getDueDate(Timestamp ts){
        if(ts != null) return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ts);
        else return "-";
    }

    public String tagWithLink(LinkedList<Tag> tgs){
        LinkedList<String> tags = new LinkedList<>();

        tgs.forEach(x -> {
            WebTag tagElem = new WebTag("a").setAttr("href", "/tags/"+x.tagId).setData(x.tagName);
            tags.add(tagElem.toString());
        });

        return tags.stream().collect(Collectors.joining(", "));
    }


    public String toHTML(CheckList chk) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("checklist");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);



        // CHECKLIST
        WebTag tableC = new WebTag("table").setAttr("class", "table table-striped").nl();
        WebTag theadC =  new WebTag("thead").nl();
        theadC.addContent(new WebTag("th").setData("Name"));
        theadC.addContent(new WebTag("th").setData("Description"));
        theadC.addContent(new WebTag("th").setData("Due Date"));
        theadC.addContent(new WebTag("th").setData("Completed"));
        theadC.addContent(new WebTag("th").setData("Tags"));
        tableC.addContent(theadC);

        WebTag tbodyC = new WebTag("tbody").nl();

        // tags to html
        //String tagsC = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));

        WebTag rowC = new WebTag("tr").nl();
        rowC.addContent(new WebTag("td").setData(chk.chkName));
        rowC.addContent(new WebTag("td").setData(chk.chkDesc));
        rowC.addContent(new WebTag("td").setData(getDueDate(chk.chkDueDate)));
        rowC.addContent(new WebTag("td").setData(chk.chkIsCompleted?"True":"False"));
        //rowC.addContent(new WebTag("td").setData(tagsC));
        rowC.addContent(new WebTag("td").setData(tagWithLink(chk.tags)));
        tbodyC.addContent(rowC);
        tableC.addContent(tbodyC);





        // TASKS
        WebTag tableT = new WebTag("table").setAttr("class", "table table-striped").nl();

        WebTag thead =  new WebTag("thead").nl();

        thead.addContent(new WebTag("th").setData("Name"));
        thead.addContent(new WebTag("th").setData("Description"));
        thead.addContent(new WebTag("th").setData("Due Date"));
        thead.addContent(new WebTag("th").setData("Completed"));

        tableT.addContent(thead);

        WebTag tbody = new WebTag("tbody").nl();

        chk.tasks.forEach(x -> {
            WebTag row = new WebTag("tr").nl();
            row.addContent(new WebTag("td").setData(x.tskName));
            row.addContent(new WebTag("td").setData(x.tskDesc));
            row.addContent(new WebTag("td").setData(getDueDate(x.tskDueDate)));
            row.addContent(new WebTag("td").setData(x.tskIsCompleted?"True":"False"));
            tbody.addContent(row);
        });

        tableT.addContent(tbody);


        WebTag main = new WebTag("div").setAttr("class", "container");
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("CheckList"));
        main.addContent(tableC);

        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Included Tasks"));
        main.addContent(tableT);

        doc.addElem(main);


//
//        String tags = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));
//
//        doc.addElem(new WebTag("p").setData(chk.chkName +" - "+ chk.chkIsCompleted+" - "+ chk.chkDueDate +" - "+ chk.chkDesc +" - [" + tags + "]"));
//        WebTag list = new WebTag("ul");
//
//        chk.tasks.forEach(x -> {
//                list.addContent(new Task2HTML().toHTML(x));
//        });
//
//        doc.addElem(list);

        return doc.toString();
    }
}
