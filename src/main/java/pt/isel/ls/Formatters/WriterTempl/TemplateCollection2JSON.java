package pt.isel.ls.Formatters.WriterTempl;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.Template;

import java.util.LinkedList;

public class TemplateCollection2JSON {

    public String toJSON(LinkedList<Template> tps) {

        JSONObject obj = new JSONObject();

        JSONObject tmpl_prop = new JSONObject();
        JSONArray tmpl_class = new JSONArray();
        JSONArray tmpl_entit = new JSONArray();

        tmpl_class.add("template");
        tmpl_class.add("collection");

        tmpl_prop.put("count", tps.size());

        if(tps.size() > 0 ) {
            tps.forEach(x -> {
                JSONObject elem = new JSONObject();

                JSONArray tmpl_classEl = new JSONArray();
                JSONObject tmpl_propEl = new JSONObject();

                tmpl_classEl.add("template");
                tmpl_propEl.put("name",x.temName);
                tmpl_propEl.put("description",x.temDesc);

                elem.put("class", tmpl_classEl);
                elem.put("properties", tmpl_propEl);

                tmpl_entit.add(elem);

            });
        }

        obj.put("class", tmpl_class);
        obj.put("properties", tmpl_prop);
        obj.put("entities", tmpl_entit);

        return obj.toJSONString();
    }
}
