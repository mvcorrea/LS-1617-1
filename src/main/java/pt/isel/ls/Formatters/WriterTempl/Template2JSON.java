package pt.isel.ls.Formatters.WriterTempl;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.Template;
import pt.isel.ls.Formatters.WriterTask.Task2JSON;

public class Template2JSON {

    public String toJSON(Template tp) {

        JSONObject obj = new JSONObject();

        JSONObject tmp_prop = new JSONObject();
        JSONArray tmp_class = new JSONArray();
        JSONArray tmp_entit = new JSONArray();

        tmp_class.add("template");

        tmp_prop.put("name", tp.temName);
        tmp_prop.put("description", tp.temDesc);

        if(tp.tasks.size() > 0 ){  // detail (checklist with tasks)

            obj.put("entities", tmp_entit);

            tp.tasks.forEach(x -> {
                tmp_entit.add(new Task2JSON().toJSON(x));
            });
        }

        if(tp.checklists.size() > 0){  // put an array with checklists id's
            JSONArray tmp_chelists = new JSONArray();
            tp.checklists.forEach( x -> {
                tmp_chelists.add(x.chkId);
            });
            tmp_prop.put("checklists", tmp_chelists);
        }

        obj.put("class", tmp_class);
        obj.put("properties", tmp_prop);

        return obj.toJSONString();
    }
}
