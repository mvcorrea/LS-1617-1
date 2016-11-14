package pt.isel.ls.Containers;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ContainerInterface <T> {
    public T fill(ResultSet rs) throws SQLException;
    //public void addTask(ResultSet rs) throws SQLException;

}
