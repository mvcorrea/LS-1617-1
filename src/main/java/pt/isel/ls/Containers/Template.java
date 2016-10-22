package pt.isel.ls.Containers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Template {
    int temId;
    public String temName, temDesc;
    LinkedList<Task> tasks = new LinkedList<>();
    LinkedList<CheckList> ckecklists = new LinkedList<>();

    public Template() {}

    public Template fill(ResultSet rs) throws SQLException {
        this.temId = rs.getInt("temId");
        this.temName = rs.getString("temName");
        this.temDesc = rs.getString("temDesc");
        return this;
    }

    public void addTask(ResultSet rs) throws SQLException {
        tasks.add(new Task().fill(rs));
    }

    public void addChecklist(ResultSet rs) throws SQLException {
        ckecklists.add(new CheckList().fill(rs));
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();

        obj.put("temId", this.temId);
        obj.put("temName", this.temName);
        obj.put("temDesc", this.temDesc);

        // Tasks List
        JSONArray tskArr = new JSONArray();
        tasks.forEach(x -> tskArr.add(x.toJSON()));
        obj.put("temTasks", tskArr);

        // Checklists List
        JSONArray chkArr = new JSONArray();
        ckecklists.forEach(x -> chkArr.add(x.toJSON()));
        obj.put("temChecklists", chkArr);

        return obj;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }


}
