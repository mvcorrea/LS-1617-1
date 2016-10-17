package pt.isel.ls.Commands;


import org.json.simple.parser.ParseException;
import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class CMD_GetCheckLstClosed implements CommandInterface{
    public static String pattern = "(GET /checklists/closed)";

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public void process(Connection con, RequestParser par) throws SQLException, GenericException, ParseException, java.text.ParseException {
        String query1 = "SELECT * FROM chklst WHERE chkIsCompleted = TRUE";
        LinkedList<CheckList> cls = new LinkedList<>();
        PreparedStatement preparedStatement = con.prepareStatement(query1);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) cls.add(new CheckList().add(rs));

        cls.forEach(System.out::println);

        preparedStatement.close();
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override
    public String toString() {
        return "GET /checklists/closed - returns a list with all completed checklists.";
    }
}
