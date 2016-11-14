package pt.isel.ls.Formatters.WriterTask;

import pt.isel.ls.Containers.Task;
import pt.isel.ls.Formatters.WebFormatter.WebTag;

public class Task2HTML {

    public WebTag toHTML(Task tsk){
        WebTag t = new WebTag("li").setData(tsk.tskName+" "+tsk.tskIsCompleted +" "+ tsk.tskDueDate+" "+ tsk.tskDesc);
        return t;
    }

}
