package pt.isel.ls.Formatters.WriterChkLst;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WriterTask.Task2TEXT;

import java.util.StringJoiner;


public class Checklist2TEXT {

    public String toTEXT(CheckList chk) {

        String out = chk.chkName +" - "+ chk.chkIsCompleted +" - "+ chk.chkDueDate +" - "+ chk.chkDesc +"\n";

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
