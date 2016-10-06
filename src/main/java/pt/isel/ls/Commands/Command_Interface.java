package pt.isel.ls.Commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public interface Command_Interface {
    public Pattern getPattern();
    public void process(Connection con) throws SQLException;
    public String toString();
}


