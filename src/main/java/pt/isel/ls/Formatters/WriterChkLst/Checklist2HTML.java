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
        //theadC.addContent(new WebTag("th"));
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
        // add tag (sign plus)
        //WebTag plus = new WebTag("a").setAttr("class","tags glyphicon glyphicon-plus");
        //rowC.addContent(new WebTag("td").setData(plus.toString()));
        tbodyC.addContent(rowC);
        tableC.addContent(tbodyC);


        // FORM TAG
        WebTag form0 = new WebTag("form").setAttr("method", "post").setAttr("action", "/checklists/"+chk.chkId+"/tags").setAttr("class","");    // do a combobox with all possible options
        WebTag formContainer0 = new WebTag("div").setAttr("class", "row center");
        formContainer0.addContent(new WebTag("spam").setAttr("class", "col-xs-4"));  // space for the combobox


        WebTag line03 = new WebTag("div").setAttr("class", "col-xs-3");
        WebTag selbox = new WebTag("select").setAttr("class", "form-control").setAttr("id", "combo").setAttr("name","gid");
        line03.addContent(selbox);
        formContainer0.addContent(line03);


        //formContainer0.addContent(new WebTag("spam").setAttr("class", ""));
        //formContainer0.addContent(selbox);

        WebTag line04 = new WebTag("div").setAttr("class", "col-xs-2");
        line04.addContent(new WebTag("button").setAttr("type", "submit").setAttr("class", "btn btn-default").setData("add Tag"));
        formContainer0.addContent(line04);
        //formContainer0.addContent(new WebTag("spam").setAttr("class", "col-xs-2"));

        form0.addContent(formContainer0);


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
            String status = x.tskIsCompleted?"True":"False";
            WebTag row = new WebTag("tr").nl();
            row.addContent(new WebTag("td").setData(x.tskName));
            row.addContent(new WebTag("td").setData(x.tskDesc));
            row.addContent(new WebTag("td").setData(getDueDate(x.tskDueDate)));
            row.addContent(new WebTag("td").addContent(new WebTag("a")
                    .setAttr("class", "status")
                    .setAttr("newstatus", x.tskIsCompleted?"False":"True")
                    .setAttr("url","/checklists/"+chk.chkId+"/tasks/"+x.tskId).setData(x.tskIsCompleted?"True":"False")));
            //row.addContent(new WebTag("td").addContent(new WebTag("a").setAttr("href",chk.chkId+"/tasks/"+x.tskId).setData(x.tskIsCompleted?"True":"False")));
            //row.addContent(new WebTag("td").setData(x.tskIsCompleted?"True":"False"));
            tbody.addContent(row);
        });

        tableT.addContent(tbody);


        // FORM TASK
        WebTag form = new WebTag("form").setAttr("method", "post").setAttr("action", "/checklists/"+chk.chkId+"/tasks").setAttr("class","");

        WebTag formContainer = new WebTag("div").setAttr("class", "row center");

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
        line5.addContent(new WebTag("button").setAttr("type", "submit").setAttr("class", "btn btn-default").setData("Create Task"));
        formContainer.addContent(line5);

        form.addContent(formContainer);



        WebTag main = new WebTag("div").setAttr("class", "container");
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("CheckList"));
        main.addContent(tableC);

        main.addContent(new WebTag("br"));
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Add New Tag"));
        main.addContent(form0);
        main.addContent(new WebTag("br"));

        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Included Tasks"));
        main.addContent(tableT);

        main.addContent(new WebTag("br"));
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Add New Task"));
        main.addContent(form);
        main.addContent(new WebTag("br"));

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
