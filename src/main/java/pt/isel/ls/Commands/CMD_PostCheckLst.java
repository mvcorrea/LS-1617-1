package pt.isel.ls.Commands;


import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Pattern;

// command sample: POST /checklists name=task1&description=my+task+descr&dueDate=20160920+1400

public class CMD_PostCheckLst extends CMD_Generic implements CommandInterface  {
    public static String pattern = "(POST /checklists)";
    public RequestParser request;
    public int chkId;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException, org.json.simple.parser.ParseException, ParseException, DBException {
        String query = "INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES (?, ?, ?)";
        this.request = par;
        try {
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, par.getParams().get("name"));
            ps.setString(2, par.getParams().get("description"));
            ps.setTimestamp(3, par.getParams().get("dueDate") != null ? str2ts(par.getParams().get("dueDate")) : null);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            this.chkId = rs.next() ? rs.getInt(1) : 0;

            // System.out.println("created checklist with id: "+ chkId);

            ps.close();
        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return false;
    }

    @Override public String toString(){
        return "POST /checklists - creates a new checklist.\n";
    }

    @Override
    public Object getData() { return this.chkId; }  // created cheklist id
}
