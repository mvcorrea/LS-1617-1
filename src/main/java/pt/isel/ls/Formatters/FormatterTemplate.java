package pt.isel.ls.Formatters;


import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WriterTempl.Template2HTML;
import pt.isel.ls.Formatters.WriterTempl.Template2JSON;
import pt.isel.ls.Formatters.WriterTempl.Template2TEXT;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;

public class FormatterTemplate implements FormatterInterface {

    public Template tmpl;
    public CommandWrapper obj;
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations

    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {

        this.obj = obj;
        this.tmpl =  (Template) obj.getCmd().getData();

        accept.put("application/json", new Template2JSON().toJSON(tmpl));
        accept.put("text/plain",       new Template2TEXT().toTEXT(tmpl));
        accept.put("text/html",        new Template2HTML().toHTML(tmpl));

        return this;

    }

    @Override
    public String format() {
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }
}
