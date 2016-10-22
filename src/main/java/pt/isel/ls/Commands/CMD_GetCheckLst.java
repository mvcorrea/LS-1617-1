package pt.isel.ls.Commands;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

// command sample: GET /checklists

public class CMD_GetCheckLst implements CommandInterface {
    public static String pattern = "(GET /checklists)";
    public LinkedList<CheckList> cls = new LinkedList<>();

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }


    // TODO: verify All Exception

    @Override
    public Object process(Connection con, RequestParser cmd) throws SQLException, GenericException {
        String query = "SELECT * FROM chklst";
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) cls.add(new CheckList().fill(rs));

            cls.forEach(System.out::println);

            ps.close();
        } catch (SQLException e){
            throw new GenericException("SQL: "+ e.getMessage());
        }
        return cls;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override
    public String toString(){
        return "GET /checklists - returns a list with all the checklists.\n";
    }
}
