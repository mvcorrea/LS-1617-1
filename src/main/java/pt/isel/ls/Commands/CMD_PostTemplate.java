package pt.isel.ls.Commands;

//import org.json.simple.parser.ParseException;

import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

// command sample: POST /templates name=miradouros&description="Miradouros de Lisboa"

public class CMD_PostTemplate implements CommandInterface {
    public static String pattern = "(POST /templates)";
    public int temId;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    // TODO: verify All Exception

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, GenericException, org.json.simple.parser.ParseException, ParseException {
        String query = "INSERT INTO templ (temName, temDesc) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, par.getParams().get("name"));
        ps.setString(2, par.getParams().get("description"));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        this.temId = rs.next() ? rs.getInt(1) : 0;

        System.out.println("created template with id: "+ temId);

        ps.close();
        return null;
    }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return false;
    }

    @Override public String toString(){
        return "POST /templates - creates a new template.\n";
    }
}
