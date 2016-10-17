package pt.isel.ls.Commands;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Helpers.CommandInterface;
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
    public RequestParser cmd;
    private Connection conn;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con, RequestParser cmd) throws SQLException {
        String query = "SELECT * FROM chklst";
        LinkedList<CheckList> cls = new LinkedList<>();
        PreparedStatement preparedStatement = con.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) cls.add(new CheckList().add(rs));

        cls.forEach(System.out::println);

        preparedStatement.close();
    }

    @Override
    public boolean validate(RequestParser par) {
        return false;
    }

    @Override public String toString(){
        return "GET /checklists - returns a list with all the checklists.\n";
    }
}
