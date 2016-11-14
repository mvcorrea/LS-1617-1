package pt.isel.ls.Formatters.WriterTempl;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WriterChkLst.Checklist2TEXT;

import java.util.LinkedList;
import java.util.StringJoiner;

public class TemplateCollection2TEXT {

    public String toTEXT(LinkedList<Template> tps) {

        StringJoiner joiner = new StringJoiner("");

        if(tps.size() > 0 ) {
            tps.forEach(x -> {
                joiner.add(new Template2TEXT().toTEXT(x));
            });
        }

        return joiner.toString();
    }
}