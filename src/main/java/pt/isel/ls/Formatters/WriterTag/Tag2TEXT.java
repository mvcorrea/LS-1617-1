package pt.isel.ls.Formatters.WriterTag;


import pt.isel.ls.Containers.Tag;

public class Tag2TEXT {
    public String toTEXT(Tag tag) {
        return tag.tagName +" - "+ tag.tagColor +"\n";
    }
}
