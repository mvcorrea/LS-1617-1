package pt.isel.ls.Formatters.WriterTempl;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TemplateCollection2HTML {
    public String toHTML(LinkedList<Template> tps) throws IOException {

        WebDocument doc = new WebDocument();
        doc.setTitle("templates");


        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);


        WebTag main = new WebTag("div").setAttr("class", "container");

        WebTag table = new WebTag("table").setAttr("class", "container table table-striped").nl();

        //thead
        WebTag thead =  new WebTag("thead").nl();
        thead.addContent(new WebTag("th").setData("Name"));
        thead.addContent(new WebTag("th").setData("Description"));
        table.addContent(thead);

        //tbody
        WebTag tbody = new WebTag("tbody").nl();
        tps.forEach(tpl -> {
            WebTag row = new WebTag("tr").nl();
            row.addContent(new WebTag("td").addContent(new WebTag("a").setAttr("href","templates/"+tpl.temId).setData(tpl.temName)));
            //row.addContent(new WebTag("td").setData(tpl.temName));
            row.addContent(new WebTag("td").setData(tpl.temDesc));
            tbody.addContent(row);
        });
        table.addContent(tbody);


        // FORM TASK
        WebTag form = new WebTag("form").setAttr("method", "post").setAttr("action", "/templates").setAttr("class","");

        WebTag formContainer = new WebTag("div").setAttr("class", "row center");

        WebTag line0 = new WebTag("spam").setAttr("class", "col-xs-1");
        formContainer.addContent(line0);

        WebTag line1 = new WebTag("div").setAttr("class", "col-xs-3");
        line1.addContent(new WebTag("label").setAttr("for", "name").setAttr("class", "sr-only").setData("Name"));
        line1.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "name").setAttr("class","form-control").setAttr("placeholder", "Name"));
        formContainer.addContent(line1);

        WebTag line2 = new WebTag("div").setAttr("class", "col-xs-5");
        line2.addContent(new WebTag("label").setAttr("for", "description").setAttr("class", "sr-only").setData("Description"));
        line2.addContent(new WebTag("input").setAttr("type", "text").setAttr("name", "description").setAttr("class","form-control col-xs-5").setAttr("placeholder", "Description"));
        formContainer.addContent(line2);

        WebTag line5 = new WebTag("div").setAttr("class", "col-xs-2");
        line5.addContent(new WebTag("button").setAttr("type", "submit").setAttr("class", "btn btn-default").setData("Create Template"));
        formContainer.addContent(line5);

        form.addContent(formContainer);





        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template(s)"));
        main.addContent(table);
        main.addContent(new WebTag("br"));
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Add New Template"));
        main.addContent(form);
        doc.addElem(main);

//        WebTag list = new WebTag("ul");
//        tps.forEach(tp -> {
//            list.addContent(new WebTag("li").setData(tp.temName +" - "+ tp.temDesc));
//        });
//
//        doc.addElem(list);

       // System.out.println(doc.toString());


        return doc.toString();
    }

}
