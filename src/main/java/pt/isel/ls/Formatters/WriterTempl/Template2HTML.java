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

        // add line
        WebTag main = new WebTag("div").setAttr("class", "container");
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template"));
        main.addContent(new WebTag("br"));
        main.addContent(tableT);
        main.addContent(new WebTag("br"));



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
        // add line
        tableTk.addContent(tbody);
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template's tasks"));
        main.addContent(new WebTag("br"));
        main.addContent(tableTk);
        main.addContent(new WebTag("br"));


        // FORM ADD TASK
        WebTag form0 = new WebTag("form").setAttr("method", "post").setAttr("action", "/templates/"+tmpl.temId+"/tasks").setAttr("class","");

        WebTag formContainer0 = new WebTag("div").setAttr("class", "row center");

        WebTag line1t = new WebTag("div").setAttr("class", "col-xs-3");
        line1t.addContent(new WebTag("label").setAttr("for", "name").setAttr("class", "sr-only").setData("Name"));
        line1t.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "name").setAttr("class","form-control").setAttr("placeholder", "Name"));
        formContainer0.addContent(line1t);

        WebTag line2t = new WebTag("div").setAttr("class", "col-xs-5");
        line2t.addContent(new WebTag("label").setAttr("for", "description").setAttr("class", "sr-only").setData("Description"));
        line2t.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "description").setAttr("class","form-control col-xs-5").setAttr("placeholder", "Description"));
        formContainer0.addContent(line2t);

//        WebTag line3t = new WebTag("div").setAttr("class", "control-label col-xs-2").setAttr("title","format: YYYYMMDD+HHMM");
//        line3t.addContent(new WebTag("label").setAttr("for", "dueDate").setAttr("class", "sr-only").setAttr("data-toggle","data-toggle").setData("Due Date"));
//        line3t.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "dueDate").setAttr("class","form-control col-xs-2").setAttr("placeholder", "Due Date"));
//        formContainer0.addContent(line3t);

        WebTag line5t = new WebTag("div").setAttr("class", "col-xs-2");
        line5t.addContent(new WebTag("button").setAttr("type", "submit").setAttr("class", "btn btn-default").setData("Create Task"));
        formContainer0.addContent(line5t);
        // add line
        form0.addContent(formContainer0);
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Add New Task"));
        main.addContent(new WebTag("br"));
        main.addContent(form0);
        main.addContent(new WebTag("br"));



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
        // add line
        tableC.addContent(tbodyC);
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template's Checklists"));
        main.addContent(new WebTag("br"));
        main.addContent(tableC);
        main.addContent(new WebTag("br"));




        // FORM ADD CHKLIST
        WebTag form1 = new WebTag("form").setAttr("method", "post").setAttr("action", "/templates/"+tmpl.temId+"/create").setAttr("class","");

        WebTag formContainer1 = new WebTag("div").setAttr("class", "row");

        WebTag line1c = new WebTag("div").setAttr("class", "col-xs-3");
        line1c.addContent(new WebTag("label").setAttr("for", "name").setAttr("class", "sr-only").setData("Name"));
        line1c.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "name").setAttr("class","form-control").setAttr("placeholder", "Name"));
        formContainer1.addContent(line1c);

        WebTag line2c = new WebTag("div").setAttr("class", "col-xs-5");
        line2c.addContent(new WebTag("label").setAttr("for", "description").setAttr("class", "sr-only").setData("Description"));
        line2c.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "description").setAttr("class","form-control col-xs-5").setAttr("placeholder", "Description"));
        formContainer1.addContent(line2c);

        WebTag line3c = new WebTag("div").setAttr("class", "control-label col-xs-2").setAttr("title","format: YYYYMMDD+HHMM");
        line3c.addContent(new WebTag("label").setAttr("for", "dueDate").setAttr("class", "sr-only").setAttr("data-toggle","data-toggle").setData("Due Date"));
        line3c.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "dueDate").setAttr("class","form-control col-xs-2").setAttr("placeholder", "Due Date"));
        formContainer1.addContent(line3c);

        WebTag line5c = new WebTag("div").setAttr("class", "col-xs-2");
        line5c.addContent(new WebTag("button").setAttr("type", "submit").setAttr("class", "btn btn-default").setData("Create Checklist"));
        formContainer1.addContent(line5c);
        // add line
        form1.addContent(formContainer1);
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Create Checklist from Template"));
        main.addContent(new WebTag("br"));
        main.addContent(form1);
        main.addContent(new WebTag("br"));


        doc.addElem(main);


        return doc.toString();
    }
}
