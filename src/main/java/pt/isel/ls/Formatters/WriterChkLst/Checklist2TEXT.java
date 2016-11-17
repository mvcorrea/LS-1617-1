package pt.isel.ls.Formatters.WriterChkLst;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WriterTask.Task2TEXT;

import java.util.StringJoiner;
import java.util.stream.Collectors;


public class Checklist2TEXT {

    public String toTEXT(CheckList chk) {

        String tags = chk.tags.stream().map(x -> x.tagName).collect(Collectors.joining(", "));

        String out = chk.chkName +" - "+ chk.chkIsCompleted +" - "+ chk.chkDueDate +" - "+ chk.chkDesc +" - ["+ tags +"]\n";

        StringJoiner joiner = new StringJoiner("\n");

        if(chk.tasks.size() > 0 ) {  // detail (checklist with tasks)
            chk.tasks.forEach(x -> {
                joiner.add(new Task2TEXT().toTEXT(x));
            });
        }

        out += joiner.toString();

        return out;
    }
}
