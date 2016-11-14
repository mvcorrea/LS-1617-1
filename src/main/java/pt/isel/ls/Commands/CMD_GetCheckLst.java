package pt.isel.ls.Commands;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

// command sample: GET /checklists

public class CMD_GetCheckLst implements CommandInterface {
    public static String pattern = "(GET /checklists)";
    public RequestParser request;
    public LinkedList<CheckList> cls = new LinkedList<>();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query = "SELECT * FROM chklst";
        this.request = par;

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) cls.add(new CheckList().fill(rs));

            //System.out.println(cmd.getHeaders().get("accept"));     // <--- OutputFormatter

            ps.close();
        } catch (SQLException e){
            throw new AppException("SQL: "+ e.getMessage());
        }
        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return false;
    }

    @Override
    public String toString(){
        return "GET /checklists - returns a list with all the checklists.\n";
    }

    @Override
    public Object getData() {
        return cls;
    }
}
