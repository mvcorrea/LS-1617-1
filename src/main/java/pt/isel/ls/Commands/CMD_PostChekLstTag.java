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
import java.util.regex.Pattern;

public class CMD_PostChekLstTag implements CommandInterface {
    public static String pattern = "(POST /checklists/\\d+/tags)";
    public RequestParser request;
    public int cid, assoc;

    @Override
    public RequestParser getRequest() {
        return request;
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query1 = "SELECT COUNT(*) FROM chk2tag WHERE xtagId = ? AND xchkId = ?"; // verify if tag relation already exists
        String query2 = "INSERT INTO chk2tag (xtagId, xchkId) VALUES (?, ?)";
        this.request = par;
        this.cid = Integer.parseInt(par.getPath()[1]);  // checklist

        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);

        try {

            con.setAutoCommit(false);

            ps1.setInt(1, Integer.valueOf(par.getParams().get("gid")));
            ps1.setInt(2, this.cid);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){
                int rowCount = rs1.getInt(1);
                System.out.println("!!>"+rowCount);
                if(rowCount == 0){ // without tag for this chklst
                    ps2.setInt(1, Integer.valueOf(par.getParams().get("gid")));
                    ps2.setInt(2, this.cid);
                    ps2.execute();
                    ResultSet rs = ps2.getGeneratedKeys();
                    this.assoc = rs.next() ? rs.getInt(1) : 0;
                }
            }
        } catch (SQLException e){
            con.rollback();
            throw new DBException( e.getMessage());
        } finally {
            con.commit();
            ps2.close();
            ps1.close();
        }


        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "POST /checklists/{cid}/tags - associate a tag to the cid.\n";
    }

    @Override
    public Object getData() {
        return assoc;
    }
}