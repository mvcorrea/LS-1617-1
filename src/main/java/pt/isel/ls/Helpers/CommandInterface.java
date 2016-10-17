package pt.isel.ls.Helpers;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.GenericException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public interface CommandInterface {
    public Pattern getPattern();
    public void process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException;
    public boolean validate(RequestParser par) throws GenericException;
    public String toString();
}


