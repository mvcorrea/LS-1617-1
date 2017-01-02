package pt.isel.ls.Formatters;


import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandWrapper;

import java.io.IOException;
import java.util.HashMap;

public class FormatterRoot implements FormatterInterface {
    public CommandWrapper obj;
    public HashMap<String,String> accept = new HashMap<>(); // map with all possible representations

    @Override
    public FormatterInterface init(CommandWrapper obj) throws IOException {
        this.obj = obj;
        accept.put("text/html",        new WriterRoot().toHTML());
        return this;
    }

    @Override
    public String format() throws AppException {
        String accType = obj.getCmd().getRequest().getHeaders().get("accept");
        return accept.get(accType);
    }
}
