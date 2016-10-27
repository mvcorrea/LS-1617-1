package pt.isel.ls.Containers;


import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tag {
    public int tagId;
    public String tagName, tagColor;

    public Tag() {}

    public Tag fill(ResultSet rs) throws SQLException {
        this.tagId = rs.getInt("tagId");
        this.tagName = rs.getString("tagName");
        this.tagColor = rs.getString("tagColor");
        return this;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("tagId", this.tagId);
        obj.put("tagName", this.tagName);
        obj.put("tagColor", this.tagColor);
        return obj;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }

}
