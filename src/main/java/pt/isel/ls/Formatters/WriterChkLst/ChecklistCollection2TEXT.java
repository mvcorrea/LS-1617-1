package pt.isel.ls.Formatters.WriterChkLst;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;

import java.util.LinkedList;
import java.util.StringJoiner;

public class ChecklistCollection2TEXT {

    public String toTEXT(LinkedList<CheckList> cls) {

        StringJoiner joiner = new StringJoiner("");

        if(cls.size() > 0 ) {  // detail (checklist with tasks)
            cls.forEach(x -> {
                joiner.add(new Checklist2TEXT().toTEXT(x));
            });
        }

        return joiner.toString();
    }
}
