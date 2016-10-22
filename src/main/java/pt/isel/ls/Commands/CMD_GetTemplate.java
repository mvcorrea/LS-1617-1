package pt.isel.ls.Commands;

import pt.isel.ls.Containers.Template;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
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
    public LinkedList<Template> tps = new LinkedList<>();

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }


    // TODO: verify All Exception

    @Override
    public Object process(Connection con, RequestParser cmd) throws SQLException, GenericException {
        String query = "SELECT * FROM templ";
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) tps.add(new Template().fill(rs));

            tps.forEach(System.out::println);

            ps.close();
        } catch (SQLException e){
            throw new GenericException("SQL: "+ e.getMessage());
        }
        return tps;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override
    public String toString(){
        return "GET /templates - returns a list with all the templates.\n";
    }
}
