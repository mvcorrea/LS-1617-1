package pt.isel.ls.Commands;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

// command sample: GET /checklists/1

public class CMD_GetCheckLstDetail implements CommandInterface {
    public static String pattern = "(GET /checklists/\\d+)";
    public RequestParser request;
    public CheckList cl = new CheckList();  // detailed checklist (with tasks)

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String query1 = "SELECT * FROM chklst WHERE chkId = ?";
        String query2 = "SELECT * FROM task WHERE tskChkId = ?";
        // associate tags with the checklist
        String query3 = "SELECT * FROM\n" +
                "  (SELECT * FROM chk2tag WHERE chk2tag.xchkId = ?) as X\n" +
                "JOIN\n" +
                "  (SELECT * FROM tag) AS Y\n" +
                " ON X.xtagId = Y.tagId";

        this.request = par;

        PreparedStatement ps = con.prepareStatement(query1);

        int cid = Integer.parseInt(par.getPath()[1]);

        try {

            con.setAutoCommit(false);

            ps.setInt(1, cid);
            ResultSet rs1 = ps.executeQuery();

            if(rs1.next()){

                cl.fill(rs1);
            } else throw new AppException("checklist ["+ cid +"] not found");

            ps = con.prepareStatement(query2);
            ps.setInt(1, cid);
            ResultSet rs2 = ps.executeQuery();

            ps = con.prepareStatement(query3);
            ps.setInt(1, cid);
            ResultSet rs3 = ps.executeQuery();

            while(rs2.next()) cl.addTask(rs2);

            while(rs3.next()) cl.addTag(rs3);




        } catch (SQLException e){
            con.rollback();
            throw new DBException( e.getMessage() );
        }finally {
            con.commit();
            ps.close();
        };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return true;
    }

    @Override
    public String toString(){
        return "GET /checklists/{cid} - returns a checklist with its tasks.\n";
    }

    @Override
    public Object getData(){ return this.cl; }
}
