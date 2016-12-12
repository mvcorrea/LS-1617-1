package pt.isel.ls.Commands;

import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.text.ParseException;
import java.util.regex.Pattern;


public class CMD_GetRoot extends CMD_Generic implements CommandInterface {
    public static String pattern = "(GET /)";
    public RequestParser request;
    public int chkId;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws Exception {
        // only allow html mode
        if(par.getHeaders().containsKey("accept") && par.getHeaders().get("accept").equals("text/html}")){

        }
        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, ParseException {
        return false;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public RequestParser getRequest() {
        return null;
    }

    @Override
    public String toString() {
        return "CMD_GetRoot";
    }

}
