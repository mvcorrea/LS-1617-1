package pt.isel.ls.Formatters.WriterChkLst;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Debug;

import java.util.LinkedList;

public class ChecklistCollection2JSON {

    public String toJSON(LinkedList<CheckList> cls) {

        JSONObject obj = new JSONObject();

        JSONObject chk_prop = new JSONObject();
        JSONArray chk_class = new JSONArray();
        JSONArray chk_entit = new JSONArray();



        chk_class.add("checklist");
        chk_class.add("collection");

        chk_prop.put("count", cls.size());


        if(cls.size() > 0 ){  // detail (checklist with tasks)

            cls.forEach(x -> {

                JSONObject elem = new JSONObject();

                JSONArray chk_classEl = new JSONArray();
                JSONObject chk_propEl = new JSONObject();

                chk_classEl.add("checklist");
                chk_propEl.put("name",x.chkName);
                chk_propEl.put("isClosed",x.chkIsCompleted);
                chk_propEl.put("dueDate",x.chkDueDate);
                chk_propEl.put("description",x.chkDesc);

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
