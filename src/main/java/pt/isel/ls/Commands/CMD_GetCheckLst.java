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
        String query1 = "SELECT * FROM chklst";
        String query2 = "SELECT * FROM (SELECT * FROM chk2tag WHERE xchkId = ?) AS X JOIN tag ON tagId = X.xtagId";

        this.request = par;

        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2);

        try {
            con.setAutoCommit(false);

            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                CheckList chk = new CheckList();
                chk.fill(rs1);  // fill ckecklist

                ps2.setInt(1, rs1.getInt("chkId"));  // put chklst id on qry2
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next()){
                    chk.addTag(rs2);   // fill tags
                }
                cls.add(chk);
            }

            //System.out.println(cmd.getHeaders().get("accept"));     // <--- OutputFormatter

        } catch (SQLException e){
            con.rollback();
            throw new AppException("SQL: "+ e.getMessage());
        } finally {
            con.commit();
            ps1.close();
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
