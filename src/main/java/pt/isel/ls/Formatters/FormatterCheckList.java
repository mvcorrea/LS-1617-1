package pt.isel.ls.Formatters;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Formatters.WriterChkLst.Checklist2HTML;
import pt.isel.ls.Formatters.WriterChkLst.Checklist2JSON;
import pt.isel.ls.Formatters.WriterChkLst.Checklist2TEXT;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;

public class FormatterCheckList implements FormatterInterface {

    public CheckList chk;
    public CommandWrapper obj;
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations


    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {
        this.obj = obj;
        this.chk =  (CheckList) obj.getCmd().getData();

        accept.put("application/json", new Checklist2JSON().toJSON(chk));
        accept.put("text/plain",       new Checklist2TEXT().toTEXT(chk));
        accept.put("text/html",        new Checklist2HTML().toHTML(chk));

        return this;
    }

    @Override
    public String format(){
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }
}
