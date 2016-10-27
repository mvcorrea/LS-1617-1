package pt.isel.ls.Commands;

import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class CMD_GetTag implements CommandInterface {
    public static String pattern = "(GET /tags)";
    public LinkedList<Tag> tgs = new LinkedList<>();

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, GenericException {
        String query = "SELECT * FROM tag";
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) tgs.add(new Tag().fill(rs));

            tgs.forEach(System.out::println);

            ps.close();
        }catch (SQLException e){
            throw new GenericException("SQL: "+ e.getMessage());
        }
        return tgs;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "CMD_GetTag{}";
    }
}
