package pt.isel.ls.Formatters.WriterTask;

import pt.isel.ls.Containers.Task;

public class Task2TEXT {

    public String toTEXT(Task tsk) {
        return "\t" + tsk.tskName + " " + tsk.tskIsCompleted +" "+ tsk.tskDueDate +" "+ tsk.tskDesc ;
    }

}
