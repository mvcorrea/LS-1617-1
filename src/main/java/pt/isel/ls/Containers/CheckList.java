package pt.isel.ls.Containers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class CheckList {
    int chkId;
    public String chkName, chkDesc;
    public Timestamp chkDueDate;
    public boolean chkIsCompleted;
    LinkedList<Task> tasks = new LinkedList<>();

    public CheckList(){}

    public CheckList fill(ResultSet rs) throws SQLException {
        this.chkId = rs.getInt("chkId");
        this.chkName = rs.getString("chkname");
        this.chkDesc = rs.getString("chkDesc");
        this.chkDueDate = rs.getTimestamp("chkDueDate");
        this.chkIsCompleted = rs.getBoolean("chkIsCompleted");
        return this;
    }

    public void addTask(ResultSet rs) throws SQLException {
        tasks.add(new Task().fill(rs));
    }

    public int numTasks(){ return tasks.size(); }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("chkId", this.chkId);
        obj.put("chkName", this.chkName);
        obj.put("chkDesc", this.chkDesc);
        if(this.chkDueDate != null) obj.put("chkDueDate", this.chkDueDate);
        obj.put("chkIsCompleted", this.chkIsCompleted);

        if(tasks.size() > 1){  // only on detail show the tasks
            JSONArray tskArr = new JSONArray();
            tasks.forEach(x -> tskArr.add(x.toJSON()));
            obj.put("temTasks", tskArr);
        }

        return obj;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }

}
