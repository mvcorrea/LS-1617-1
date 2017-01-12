package pt.isel.ls.Formatters.WriterTag;


import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Formatters.WebFormatter.WebDocument;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class Tag2HTML {

    public String getDueDate(Timestamp ts){
        if(ts != null) return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ts);
        else return "-";
    }

    public String toHTML(Tag tag) throws IOException {
        WebDocument doc = new WebDocument();
        doc.setTitle("Tag");

        // menu
        WebTag menu = new WebTag("ul").setAttr("class", "nav navbar-nav navbar-right");
        menu.addContent(new WebTag("li").addContent(new WebTag("a").setAttr("href","javascript:history.go(-1)").setData("Back")));
        doc.setMenu(menu);

        // tag
        WebTag tableC = new WebTag("table").setAttr("class", "table table-striped").nl();
        WebTag theadC =  new WebTag("thead").nl();
        theadC.addContent(new WebTag("th").setData("Name"));
        theadC.addContent(new WebTag("th").setData("Color"));
        tableC.addContent(theadC);

        WebTag tbodyC = new WebTag("tbody").nl();

        WebTag rowC = new WebTag("tr").nl();
        rowC.addContent(new WebTag("td").addContent(new WebTag("a")
                .setAttr("href","/tags/"+tag.tagId+"/checklists").setData(tag.tagName)));
        //rowC.addContent(new WebTag("td").setData(tag.tagName));
        rowC.addContent(new WebTag("td").setData(tag.tagColor));


        // checklists with that tag
        WebTag table = new WebTag("table").setAttr("class", "table table-striped").nl();

        WebTag thead =  new WebTag("thead").nl();
        thead.addContent(new WebTag("th").setData("Name"));
        thead.addContent(new WebTag("th").setData("Description"));
        thead.addContent(new WebTag("th").setData("Due Date"));
        thead.addContent(new WebTag("th").setData("Completed"));
        //thead.addContent(new WebTag("th").setData("Tags"));
        table.addContent(thead);

        WebTag tbody = new WebTag("tbody").nl();
        System.out.println(tag.chks.size());
        tag.chks.forEach(chk -> {
            System.out.println(chk.chkName);
            //String tags = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));
            WebTag row = new WebTag("tr").nl();
            row.addContent(new WebTag("td").addContent(new WebTag("a").setAttr("href","/checklists/"+chk.chkId).setData(chk.chkName)));
            //row.addContent(new WebTag("td").setData(chk.chkName));
            row.addContent(new WebTag("td").setData(chk.chkDesc));
            row.addContent(new WebTag("td").setData(getDueDate(chk.chkDueDate)));
            row.addContent(new WebTag("td").setData(chk.chkIsCompleted?"True":"False"));
            //row.addContent(new WebTag("td").setData(tags));
            tbody.addContent(row);

        });
        table.addContent(tbody);




        tbodyC.addContent(rowC);
        tableC.addContent(tbodyC);


        WebTag main = new WebTag("div").setAttr("class", "container");
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Tag"));
        main.addContent(tableC);
        main.addContent(new WebTag("br"));
        main.addContent(new WebTag("h4").setAttr("class", "text-center").setData("Checklists"));
        main.addContent(table);


        doc.addElem(main);


        return doc.toString();
    }

}
