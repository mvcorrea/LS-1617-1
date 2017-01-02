package pt.isel.ls.Formatters.WriterTag;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.Tag;


import java.util.LinkedList;

public class TagCollection2JSON {

    public String toJSON(LinkedList<Tag> tg) {

        JSONObject obj = new JSONObject();

        JSONObject chk_prop = new JSONObject();
        JSONArray chk_class = new JSONArray();
        JSONArray chk_entit = new JSONArray();



        chk_class.add("tag");
        chk_class.add("collection");

        chk_prop.put("count", tg.size());


        if(tg.size() > 0 ){  // detail (checklist with tasks)

            tg.forEach(x -> {

                JSONObject elem = new JSONObject();

                JSONArray chk_classEl = new JSONArray();
                JSONObject chk_propEl = new JSONObject();

                chk_classEl.add("tag");
                chk_propEl.put("id",x.tagId);
                chk_propEl.put("name",x.tagName);
                chk_propEl.put("color",x.tagColor);

                elem.put("class", chk_classEl);
                elem.put("properties", chk_propEl);

                chk_entit.add(elem);

            });
        }

        obj.put("class", chk_class);
        obj.put("properties", chk_prop);
        obj.put("entities", chk_entit);

        return obj.toJSONString();

    }
}
