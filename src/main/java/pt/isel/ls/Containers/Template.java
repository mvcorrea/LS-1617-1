package pt.isel.ls.Containers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Template implements ContainerInterface {
    int temId;
    public String temName, temDesc;
    public LinkedList<Task> tasks = new LinkedList<>();
    public LinkedList<CheckList> checklists = new LinkedList<>();

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
        checklists.add(new CheckList().fill(rs));
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
        //return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }


}
