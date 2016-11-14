package pt.isel.ls.Helpers;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public interface CommandInterface {
    public Pattern getPattern();
    public Object process(Connection con, RequestParser par) throws SQLException, AppException, ParseException, java.text.ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, DBException;
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException;
    public String toString();
    public Object getData();
    public RequestParser getRequest();
}


