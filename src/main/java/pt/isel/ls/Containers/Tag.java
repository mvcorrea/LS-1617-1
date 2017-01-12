package pt.isel.ls.Containers;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Tag implements ContainerInterface {
    public int tagId;
    public String tagName, tagColor;
    public LinkedList<CheckList> chks = new LinkedList<>();

    public Tag() {}

    public Tag fill(ResultSet rs) throws SQLException {
        this.tagId = rs.getInt("tagId");
        this.tagName = rs.getString("tagName");
        this.tagColor = rs.getString("tagColor");
        return this;
    }

    public void fillChks(){

    }

    public Tag getTag(){
        return this;
    }
}
