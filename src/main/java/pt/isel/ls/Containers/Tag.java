package pt.isel.ls.Containers;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tag implements ContainerInterface {
    public int tagId;
    public String tagName, tagColor;

    public Tag() {}

    public Tag fill(ResultSet rs) throws SQLException {
        this.tagId = rs.getInt("tagId");
        this.tagName = rs.getString("tagName");
        this.tagColor = rs.getString("tagColor");
        return this;
    }

    public Tag getTag(){
        return this;
    }
}
