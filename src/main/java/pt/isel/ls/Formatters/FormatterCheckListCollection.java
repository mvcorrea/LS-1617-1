package pt.isel.ls.Formatters;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Formatters.WriterChkLst.ChecklistCollection2HTML;
import pt.isel.ls.Formatters.WriterChkLst.ChecklistCollection2JSON;
import pt.isel.ls.Formatters.WriterChkLst.ChecklistCollection2TEXT;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class FormatterCheckListCollection implements FormatterInterface{

    public CommandWrapper obj;
    public LinkedList<CheckList> cls = new LinkedList<>();
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations

    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {
        this.obj = obj;
        this.cls = (LinkedList<CheckList>) obj.getCmd().getData();

        accept.put("application/json", new ChecklistCollection2JSON().toJSON(cls));
        accept.put("text/plain",       new ChecklistCollection2TEXT().toTEXT(cls));
        accept.put("text/html",        new ChecklistCollection2HTML().toHTML(cls));

        return this;
    }

    @Override
    public String format(){
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }

}
