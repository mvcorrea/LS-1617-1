package pt.isel.ls.Formatters;


import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WriterTempl.TemplateCollection2HTML;
import pt.isel.ls.Formatters.WriterTempl.TemplateCollection2JSON;
import pt.isel.ls.Formatters.WriterTempl.TemplateCollection2TEXT;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class FormatterTemplateCollection implements FormatterInterface{

    public CommandWrapper obj;
    public LinkedList<Template> cls = new LinkedList<>();
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations

    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {
        this.obj = obj;
        this.cls = (LinkedList<Template>) obj.getCmd().getData();

        accept.put("application/json", new TemplateCollection2JSON().toJSON(cls));
        accept.put("text/plain",       new TemplateCollection2TEXT().toTEXT(cls));
        accept.put("text/html",        new TemplateCollection2HTML().toHTML(cls));

        return this;
    }

    @Override
    public String format() {
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }
}
