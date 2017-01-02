package pt.isel.ls.Containers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Task implements ContainerInterface {
    public int tskId;
    public String tskName, tskDesc;
    public Timestamp tskDueDate;
    public boolean tskIsCompleted;

    public Task(){}

    public Task fill(ResultSet rs) throws SQLException {
        this.tskId = rs.getInt("tskId");
        this.tskName = rs.getString("tskName");
        this.tskDesc = rs.getString("tskDesc");
        this.tskDueDate = rs.getTimestamp("tskDueDate");
        this.tskIsCompleted = rs.getBoolean("tskIsCompleted");
        return this;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
        //return this.getClass().getSimpleName() +": "+this.toJSON().toJSONString();
        // check: http://jsonviewer.stack.hu/
    }
}
