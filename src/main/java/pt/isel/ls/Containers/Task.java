package pt.isel.ls.Containers;

import org.json.simple.JSONObject;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class Task {
    int tskId;
    String tskName, tskDesc;
    Timestamp tskDueDate;
    boolean tskIsCompleted;

    public Task(){}

    public Task fill(ResultSet rs) throws SQLException {
        this.tskId = rs.getInt("tskId");
        this.tskName = rs.getString("tskName");
        this.tskDesc = rs.getString("tskDesc");
        this.tskDueDate = rs.getTimestamp("tskDueDate");
        this.tskIsCompleted = rs.getBoolean("tskIsCompleted");
        return this;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("tskId", this.tskId);
        obj.put("tskName", this.tskName);
        obj.put("tskDesc", this.tskDesc);
        if(this.tskDueDate != null) obj.put("tskDueDate", this.tskDueDate.toString());
        obj.put("tskIsCompleted", this.tskIsCompleted);
        return obj;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }
}
