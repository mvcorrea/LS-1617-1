package pt.isel.ls.Formatters;

import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Formatters.WriterTag.TagCollection2HTML;
import pt.isel.ls.Formatters.WriterTag.TagCollection2JSON;
import pt.isel.ls.Formatters.WriterTag.TagCollection2TEXT;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class FormatterTagCollection implements FormatterInterface {
    public LinkedList<Tag> tgs;
    public CommandWrapper obj;
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations


    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {
        this.obj = obj;
        this.tgs =  (LinkedList<Tag>) obj.getCmd().getData();

        accept.put("application/json", new TagCollection2JSON().toJSON(tgs));
        accept.put("text/plain",       new TagCollection2TEXT().toTEXT(tgs));
        accept.put("text/html",        new TagCollection2HTML().toHTML(tgs));

        return this;
    }

    @Override
    public String format(){
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }
}
