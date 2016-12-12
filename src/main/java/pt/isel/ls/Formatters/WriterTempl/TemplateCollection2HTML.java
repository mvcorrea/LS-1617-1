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

        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Template(s)"));
        main.addContent(table);

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
