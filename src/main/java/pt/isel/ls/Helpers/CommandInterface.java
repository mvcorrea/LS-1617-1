package pt.isel.ls.Helpers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public interface CommandInterface {
    public Pattern getPattern();
    public void process(Connection con, RequestParser par) throws SQLException;
    public boolean validate(RequestParser par);
    public String toString();
}


