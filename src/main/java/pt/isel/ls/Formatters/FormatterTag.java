package pt.isel.ls.Formatters;


import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Formatters.WriterTag.Tag2HTML;
import pt.isel.ls.Formatters.WriterTag.Tag2JSON;
import pt.isel.ls.Formatters.WriterTag.Tag2TEXT;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;

public class FormatterTag implements FormatterInterface {

    public Tag tag;
    public CommandWrapper obj;
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations

    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {
        this.obj = obj;
        this.tag = (Tag) obj.getCmd().getData();

        accept.put("application/json", new Tag2JSON().toJSON(tag));
        accept.put("text/plain",       new Tag2TEXT().toTEXT(tag));
        accept.put("text/html",        new Tag2HTML().toHTML(tag));

        return this;
    }

    @Override
    public String format() throws AppException {
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }
}
