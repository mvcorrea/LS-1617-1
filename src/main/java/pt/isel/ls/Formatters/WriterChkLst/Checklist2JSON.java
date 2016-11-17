package pt.isel.ls.Formatters.WriterChkLst;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;
import pt.isel.ls.Formatters.WriterTask.Task2JSON;


public class Checklist2JSON {

    public String toJSON(CheckList chk) {

        JSONObject obj = new JSONObject();

        JSONObject chk_prop = new JSONObject();
        JSONArray chk_class = new JSONArray();
        JSONArray chk_entit = new JSONArray();

        chk_class.add("checklist");

        chk_prop.put("name", chk.chkName);
        chk_prop.put("isClosed", chk.chkIsCompleted);
        chk_prop.put("dueDate", ""+chk.chkDueDate+"");
        chk_prop.put("description", chk.chkDesc);

        if(chk.tasks.size() > 0 ){  // detail (checklist with tasks)

            obj.put("entities", chk_entit);

            chk.tasks.forEach(x -> {
                chk_entit.add(new Task2JSON().toJSON(x));
            });
        }

        if(chk.tags.size() > 0){
            JSONArray chk_tags = new JSONArray();
            chk.tags.forEach(x -> {
                chk_tags.add(x.tagName);
            });
            chk_prop.put("tags", chk_tags);
        }



        obj.put("class", chk_class);
        obj.put("properties", chk_prop);

        return obj.toJSONString();
    }

}
