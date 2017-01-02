package pt.isel.ls.Commands;


import pt.isel.ls.Containers.Tag;
import pt.isel.ls.Debug;
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

public class CMD_GetTagDetail implements CommandInterface {
    public static String pattern = "(GET /tags/\\d+)";
    public RequestParser request;
    public Tag tp = new Tag();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws Exception {
        // get the template
        String query1 = "SELECT * FROM tag WHERE tagId = ?";

        this.request = par;
        int tid = Integer.parseInt(par.getPath()[1]);

        try {

            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, tid);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){
                tp.fill(rs1);
            } else throw new DBException("tag ["+ tid +"] not found");


            ps1.close();


        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, ParseException {
        return true;
    }

    @Override
    public Object getData() {
        return this.tp;
    }

    @Override
    public String toString(){
        return "GET /tags/{cid} - returns a tag.\n";
    }


}
