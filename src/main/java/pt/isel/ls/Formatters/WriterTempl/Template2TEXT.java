package pt.isel.ls.Formatters.WriterTempl;


import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WriterTask.Task2TEXT;

import java.util.StringJoiner;

public class Template2TEXT {
    public String toTEXT(Template tmp) {

        String out = tmp.temName +" - "+ tmp.temDesc +" - [";

        StringJoiner joiner = new StringJoiner(", ");
        if(tmp.checklists.size() > 0){
            String checklists = "";
            tmp.checklists.forEach( x -> {
                joiner.add(String.valueOf(x.chkId));
            });
            out += joiner.toString()+"]\n";
        }
        StringJoiner joiner1 = new StringJoiner("\n");
        if(tmp.tasks.size() > 0 ) {  // detail (checklist with tasks)
            tmp.tasks.forEach(x -> {
                joiner1.add(new Task2TEXT().toTEXT(x));
            });
            out += joiner1.toString();
        }

        return out;
    }
}
