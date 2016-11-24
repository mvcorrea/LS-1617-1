package pt.isel.ls.Commands;

import pt.isel.ls.Containers.Template;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

// command sample: GET /templates

public class CMD_GetTemplate implements CommandInterface {
    public static String pattern = "(GET /templates)";
    public RequestParser request;
    public LinkedList<Template> tps = new LinkedList<>();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query = "SELECT * FROM templ";
        this.request = par;
        PreparedStatement ps = con.prepareStatement(query);


        try {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) tps.add(new Template().fill(rs));

            //tps.forEach(System.out::println);

        } catch (SQLException e){
            throw new DBException( e.getMessage() );
        } finally {
            ps.close();
        };


    return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return false;
    }

    @Override
    public String toString(){
        return "GET /templates - returns a list with all the templates.\n";
    }

    @Override
    public Object getData() { return tps; }
}
