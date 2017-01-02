package pt.isel.ls.Formatters.WriterTag;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pt.isel.ls.Containers.Tag;

public class Tag2JSON {
    public String toJSON(Tag tag) {
        JSONObject obj = new JSONObject();

        JSONObject tag_prop = new JSONObject();
        JSONArray tag_class = new JSONArray();

        tag_class.add("tag");

        tag_prop.put("tagid", tag.tagId);
        tag_prop.put("name", tag.tagName);
        tag_prop.put("color", tag.tagColor);

        obj.put("class", tag_class);
        obj.put("properties", tag_prop);

        return obj.toJSONString();
    }
}
