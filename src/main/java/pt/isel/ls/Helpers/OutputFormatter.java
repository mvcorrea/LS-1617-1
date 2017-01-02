package pt.isel.ls.Helpers;

import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Formatters.*;
import pt.isel.ls.Debug;

import java.io.IOException;
import java.util.HashMap;

public class OutputFormatter {

    CommandWrapper cmd;
    public HashMap<String,FormatterInterface> container = new HashMap<>();

    public OutputFormatter() throws IOException {

        // phase 01
        container.put("CMD_GetCheckLstDetail",      new FormatterCheckList());
        container.put("CMD_GetCheckLst",            new FormatterCheckListCollection());
        container.put("CMD_GetCheckLstClosed",      new FormatterCheckListCollection());
        container.put("CMD_GetCheckLstOpenDueDate", new FormatterCheckListCollection());
        container.put("CMD_GetTemplate",            new FormatterTemplateCollection());
        container.put("CMD_GetTemplateDetail",      new FormatterTemplate());
        container.put("CMD_GetCheckLstOpenNumTsks", new FormatterCheckListCollection());

        // phase 02
        container.put("CMD_GetTag",                 new FormatterTagCollection());

        // phase 03
        container.put("CMD_GetRoot",                new FormatterRoot());
        container.put("CMD_GetTagDetail",           new FormatterTag());
        container.put("CMD_GetTagChekLst",          new FormatterCheckListCollection());

    }

    public String format(Object obj) throws IOException, AppException {
        this.cmd = (CommandWrapper) obj;
        if(Debug.ON) System.out.println("> OutputFormatter: "+ cmd.getCmd().getClass().getSimpleName());
        String cont = cmd.getCmd().getClass().getSimpleName();
        return container.get(cont).init(cmd).format();
    }
}
