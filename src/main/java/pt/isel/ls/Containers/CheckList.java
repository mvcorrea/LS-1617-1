package pt.isel.ls.Containers;

import pt.isel.ls.Helpers.RequestParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class CheckList implements ContainerInterface {
    public int chkId;
    public String chkName, chkDesc;
    public Timestamp chkDueDate;
    public boolean chkIsCompleted;
    public LinkedList<Task> tasks = new LinkedList<>();
    public LinkedList<Tag> tags = new LinkedList<>();
    public RequestParser req;

    public CheckList() { }

    // fill the chklist from a db resultset
    @Override
    public CheckList fill(ResultSet rs) throws SQLException {
        this.chkId = rs.getInt("chkId");
        this.chkName = rs.getString("chkname");
        this.chkDesc = rs.getString("chkDesc");
        this.chkDueDate = rs.getTimestamp("chkDueDate");
        this.chkIsCompleted = rs.getBoolean("chkIsCompleted");
        return this;
    }

    // fill tasks list (when looking for chklist details)
    public void addTask(ResultSet rs) throws SQLException {
        tasks.add(new Task().fill(rs));
    }

    // fill tags list
    public void addTag(ResultSet rs) throws SQLException {
        tags.add(new Tag().fill(rs));
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
        //return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }

}
