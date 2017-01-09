package pt.isel.ls.Commands;


import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.regex.Pattern;

/* get checklist that include the tag */


public class CMD_GetTagChekLst implements CommandInterface {
    public static String pattern = "(GET /tags/\\d+/checklists)";
    public RequestParser request;
    public LinkedList<CheckList> cls = new LinkedList<>();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws Exception {
        String query1 = "SELECT * FROM\n" +
                "  (SELECT xtagId, xchkId FROM chk2tag WHERE xtagId = ?) as X\n" +
                "JOIN\n" +
                "  (SELECT * FROM chklst) AS Y\n" +
                "ON X.xchkId = Y.chkId";

        this.request = par;
        int gid = Integer.parseInt(par.getPath()[1]);
        PreparedStatement ps1 = con.prepareStatement(query1);

        try {

            ps1.setInt(1, gid);
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                CheckList chk = new CheckList();
                chk.fill(rs1);  // fill ckecklist
                cls.add(chk);

            }

        } catch (SQLException e){
            throw new AppException("SQL: "+ e.getMessage());
        } finally {
            ps1.close();
        }

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, ParseException {
        return false;
    }

    @Override
    public Object getData() {
        return cls;
    }


}
