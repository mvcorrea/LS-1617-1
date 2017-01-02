package pt.isel.ls.Formatters.WriterTag;


import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Formatters.WriterChkLst.Checklist2TEXT;

import java.util.LinkedList;
import java.util.StringJoiner;

public class TagCollection2TEXT {
    public String toTEXT(LinkedList<Tag> cls) {

        StringJoiner joiner = new StringJoiner("");

        if(cls.size() > 0 ) {  // detail (checklist with tasks)
            cls.forEach(x -> {
                joiner.add(x.tagName +" - "+ x.tagColor + "\n");
            });
        }

        return joiner.toString();
    }
}
