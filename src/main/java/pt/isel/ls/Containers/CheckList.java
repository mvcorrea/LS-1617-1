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

    // TODO: ASK if all checklist actualization could stay here?

    public CheckList(int chkId, String chkName, String chkDesc, Timestamp chkDueDate, boolean chkIsCompleted) {
        this.chkId = chkId;
        this.chkName = chkName;
        this.chkDesc = chkDesc;
        this.chkDueDate = chkDueDate;
        this.chkIsCompleted = chkIsCompleted;
    }

    public CheckList fill(ResultSet rs) throws SQLException {
        this.chkId = rs.getInt("chkId");
        this.chkName = rs.getString("chkname");
        this.chkDesc = rs.getString("chkDesc");
        this.chkDueDate = rs.getTimestamp("chkDueDate");
        this.chkIsCompleted = rs.getBoolean("chkIsCompleted");
        return this;
    }

    public void addTask(ResultSet rs) throws SQLException {
        tasks.add(new Task().add(rs));
    }

    public JSONArray lst2JsonArr() throws ParseException {
        JSONArray arr = new JSONArray();
        tasks.forEach(x -> {
            JSONObject json = null;
            try {
                json = (JSONObject) new JSONParser().parse(x.toString());
            } catch (ParseException e) {
                e.printStackTrace();            // TODO: manage exceptions :)
            }
            arr.add(json);
        });
        return arr;
    }

    public int numTasks(){ return tasks.size();}

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("chkId", this.chkId);
        obj.put("chkName", this.chkName);
        obj.put("chkDesc", this.chkDesc);
        obj.put("chkDueDate", this.chkDueDate);
        obj.put("chkIsCompleted", this.chkIsCompleted);
        if(tasks.size() != 0){ // only show tasks when in detail
            try {
                obj.put("chkTasks", lst2JsonArr());
            } catch (ParseException e) {
                e.printStackTrace();            // TODO: manage exceptions :)
            }
        }
        return this.getClass().getSimpleName() +": "+ obj.toJSONString();
        // check: http://jsonviewer.stack.hu/
    }

//    @Override
//    public String toString() {
//        return "CheckList: { " +
//                "chkId = " + chkId +
//                ", chkName = '" + chkName + '\'' +
//                ", chkDesc = '" + chkDesc + '\'' +
//                ", chkDueDate = " + chkDueDate +
//                ", chkIsCompleted = " + chkIsCompleted +
//                " }";
//    }

}
