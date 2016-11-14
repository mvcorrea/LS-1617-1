package pt.isel.ls.Formatters.WriterTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.Task;

public class Task2JSON {

    public JSONObject toJSON(Task tsk) {
        JSONObject obj = new JSONObject();

        JSONObject tsk_prop = new JSONObject();

        JSONArray tsk_class = new JSONArray();
        tsk_class.add("task");

        tsk_prop.put("name", tsk.tskName);
        tsk_prop.put("isClosed", tsk.tskIsCompleted);
        tsk_prop.put("isClosed", tsk.tskDueDate);
        tsk_prop.put("description", tsk.tskDesc);

        obj.put("class", tsk_class);
        obj.put("properties", tsk_prop);

        return obj;
    }

}
