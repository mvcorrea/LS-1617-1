package pt.isel.ls.Containers;


import org.json.simple.JSONObject;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Task {
    int tskId;
    String tskName, tskDesc;
    Timestamp tskDueDate;
    boolean tskIsCompleted;

    public Task(){}

    public Task(int tskId, String tskName, String tskDesc, Timestamp tskDueDate, boolean tskIsCompleted) {
        this.tskId = tskId;
        this.tskName = tskName;
        this.tskDesc = tskDesc;
        this.tskDueDate = tskDueDate;
        this.tskIsCompleted = tskIsCompleted;
    }

    public Task add(ResultSet rs) throws SQLException {
        this.tskId = rs.getInt("tskId");
        this.tskName = rs.getString("tskName");
        this.tskDesc = rs.getString("tskDesc");
        this.tskDueDate = rs.getTimestamp("tskDueDate");
        this.tskIsCompleted = rs.getBoolean("tskIsCompleted");
        return this;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("tskId", this.tskId);
        obj.put("tskName", this.tskName);
        obj.put("tskDesc", this.tskDesc);
        obj.put("tskDueDate", this.tskDueDate.toString());
        obj.put("tskIsCompleted", this.tskIsCompleted);
        return obj.toJSONString();
    }
    
    
//    @Override
//    public String toString() {
//        return "Task { " +
//                " tskId = " + tskId +
//                ", tskName = '" + tskName + '\'' +
//                ", tskDesc = '" + tskDesc + '\'' +
//                ", tskDueDate = " + tskDueDate +
//                ", tskIsCompleted = " + tskIsCompleted +
//                " }";
//    }
}
