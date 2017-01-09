package pt.isel.ls.Formatters.WriterTempl;

import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;
import pt.isel.ls.Formatters.WriterTask.Task2HTML;
import pt.isel.ls.Formatters.WriterTask.Task2TEXT;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Template2HTML {

    public String getDueDate(Timestamp ts){
        if(ts != null) return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ts);
        else return "-";
    }

    public String toHTML(Template tmpl) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("template");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);


        // TEMPLATE
        WebTag tableT = new WebTag("table").setAttr("class", "table table-striped").nl();
        WebTag theadT =  new WebTag("thead").nl();
        theadT.addContent(new WebTag("th").setData("Name"));
        theadT.addContent(new WebTag("th").setData("Description"));
        tableT.addContent(theadT);

        WebTag tbodyT = new WebTag("tbody").nl();
        WebTag rowT = new WebTag("tr").nl();
        rowT.addContent(new WebTag("td").setData(tmpl.temName));
        rowT.addContent(new WebTag("td").setData(tmpl.temDesc));
        tbodyT.addContent(rowT);
        tableT.addContent(tbodyT);


        WebTag main = new WebTag("div").setAttr("class", "container");
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template"));
        main.addContent(tableT);



        // TASKS
        WebTag tableTk = new WebTag("table").setAttr("class", "table table-striped").nl();

        WebTag theadTk =  new WebTag("thead").nl();

        theadTk.addContent(new WebTag("th").setData("Name"));
        theadTk.addContent(new WebTag("th").setData("Description"));
        theadTk.addContent(new WebTag("th").setData("Due Date"));
        theadTk.addContent(new WebTag("th").setData("Completed"));

        tableTk.addContent(theadTk);

        WebTag tbody = new WebTag("tbody").nl();

        tmpl.tasks.forEach(x -> {
            WebTag rowTk = new WebTag("tr").nl();
            rowTk.addContent(new WebTag("td").setData(x.tskName));
            rowTk.addContent(new WebTag("td").setData(x.tskDesc));
            rowTk.addContent(new WebTag("td").setData(getDueDate(x.tskDueDate)));
            rowTk.addContent(new WebTag("td").setData(x.tskIsCompleted?"True":"False"));
            tbody.addContent(rowTk);
        });

        tableTk.addContent(tbody);
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template's tasks"));
        main.addContent(tableTk);


        // CHECKLISTS
        WebTag tableC = new WebTag("table").setAttr("class", "table table-striped").nl();
        WebTag theadC =  new WebTag("thead").nl();
        theadC.addContent(new WebTag("th").setData("Name"));
        theadC.addContent(new WebTag("th").setData("Description"));
        theadC.addContent(new WebTag("th").setData("Due Date"));
        theadC.addContent(new WebTag("th").setData("Completed"));
        //theadC.addContent(new WebTag("th").setData("Tags"));
        tableC.addContent(theadC);

        WebTag tbodyC = new WebTag("tbody").nl();
        tmpl.checklists.forEach(chk -> {
           // String tags = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));
            WebTag row = new WebTag("tr").nl();
            row.addContent(new WebTag("td").addContent(new WebTag("a").setAttr("href","/checklists/"+chk.chkId).setData(chk.chkName)));
            //row.addContent(new WebTag("td").setData(chk.chkName));
            row.addContent(new WebTag("td").setData(chk.chkDesc));
            row.addContent(new WebTag("td").setData(getDueDate(chk.chkDueDate)));
            row.addContent(new WebTag("td").setData(chk.chkIsCompleted?"True":"False"));
            //row.addContent(new WebTag("td").setData(tags));
            tbodyC.addContent(row);
        });

        tableC.addContent(tbodyC);



        // FORM
        WebTag form = new WebTag("form").setAttr("method", "post").setAttr("action", "/templates/"+tmpl.temId+"/create").setAttr("class","");

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



        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template's Checklists"));
        main.addContent(tableC);
        main.addContent(new WebTag("br"));
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Create Checklist from Template"));
        main.addContent(form);

        doc.addElem(main);


//        StringJoiner joiner = new StringJoiner(", ");
//        if(tmpl.checklists.size() > 0){
//            tmpl.checklists.forEach( x -> {
//                joiner.add(String.valueOf(x.chkId));
//            });
//        }
//
//        doc.addElem(new WebTag("p").setData(tmpl.temName +" - "+  tmpl.temDesc +" - ["+ joiner.toString() +"]"));
//        WebTag list = new WebTag("ul");
//
//
//
//        if(tmpl.tasks.size() > 0 ) {  // detail (checklist with tasks)
//            tmpl.tasks.forEach(x -> {
//                list.addContent(new Task2HTML().toHTML(x));
//            });
//        }
//
//        doc.addElem(list);

        return doc.toString();
    }
}
