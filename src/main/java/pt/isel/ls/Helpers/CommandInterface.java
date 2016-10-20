package pt.isel.ls.Helpers;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Commands.CMD_GetCheckLstDetail;
import pt.isel.ls.Exceptions.GenericException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public interface CommandInterface {
    public Pattern getPattern();
    public CommandWrapper process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException;
    public Object getData();
    public boolean validate(RequestParser par) throws GenericException, java.text.ParseException;
    public String toString();
}


