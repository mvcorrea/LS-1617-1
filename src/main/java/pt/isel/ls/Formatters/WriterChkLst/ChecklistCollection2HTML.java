package pt.isel.ls.Formatters.WriterChkLst;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;
import pt.isel.ls.Formatters.WebFormatter.WebText;

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

        // FORM
        WebTag form = new WebTag("form").setAttr("method", "post").setAttr("action", "/checklists").setAttr("class","");

        WebTag formContainer = new WebTag("div").setAttr("class", "row");

        WebTag line1 = new WebTag("div").setAttr("class", "col-xs-3");
        line1.addContent(new WebTag("label").setAttr("for", "name").setAttr("class", "sr-only").setData("Name"));
        line1.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "name").setAttr("class","form-control").setAttr("placeholder", "Name"));
        formContainer.addContent(line1);

        WebTag line2 = new WebTag("div").setAttr("class", "col-xs-5");
        line2.addContent(new WebTag("label").setAttr("for", "description").setAttr("class", "sr-only").setData("Description"));
        line2.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "description").setAttr("class","form-control col-xs-5").setAttr("placeholder", "Description"));
        formContainer.addContent(line2);

        WebTag line3 = new WebTag("div").setAttr("class", "control-label col-xs-2").setAttr("title","format: YYYYMMDD+HHMM");
        line3.addContent(new WebTag("label").setAttr("for", "dueDate").setAttr("class", "sr-only").setAttr("data-toggle","data-toggle").setData("Due Date"));
        line3.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "dueDate").setAttr("class","form-control col-xs-2").setAttr("placeholder", "Due Date"));
        formContainer.addContent(line3);

        WebTag line5 = new WebTag("div").setAttr("class", "col-xs-2");
        line5.addContent(new WebTag("button").setAttr("type", "submit").setAttr("class", "btn btn-default").setData("Create Checklist"));
        formContainer.addContent(line5);

        form.addContent(formContainer);




        // format page
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("CheckList(s)"));
        main.addContent(table);
        main.addContent(new WebTag("br"));
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Add New CheckList"));
        main.addContent(form);
        main.addContent(new WebTag("br"));

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
