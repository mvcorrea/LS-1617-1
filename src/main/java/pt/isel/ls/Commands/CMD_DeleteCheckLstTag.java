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

public class CMD_DeleteCheckLstTag implements CommandInterface {
    public static String pattern = "(DELETE /checklists/\\d+/tags/\\d+)";
    public RequestParser request;
    public int cid, gid, xId;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        String query1 = "SELECT xId FROM chk2tag WHERE xchkId=? AND xtagId=?";
        String query2 = "DELETE FROM chk2tag WHERE xId = ?";
        this.request = par;

        this.cid = Integer.parseInt(par.getPath()[1]);
        this.gid = Integer.parseInt(par.getPath()[3]);

        try {

            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, this.cid);
            ps1.setInt(2, this.gid);
            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next()){
                rs1.first();
                this.xId = Integer.parseInt(rs1.getString("xId"));
            }

            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, this.xId);
            int res = ps2.executeUpdate();

            //System.out.println("deleted associaation of group  ["+gid+"] from CheckList ["+cid+"]");

            con.commit();

            ps1.close();
            ps2.close();

        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public String toString() {
        return "DELETE /checklists/{cid}/tags/{gid} - deletes the association between the cid checklist and the gid tag.\n";
    }

    @Override
    public Object getData() {
        return this.xId;
    }

}
