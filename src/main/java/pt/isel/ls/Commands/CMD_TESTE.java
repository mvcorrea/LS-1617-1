package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
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


public class CMD_TESTE extends CMD_Generic implements CommandInterface {

    public static String pattern = "(POST /templates/\\d+/checklists/notclosed/tags)";
    public RequestParser request;
    public int tid, gid, res;

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException, ParseException, java.text.ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, DBException {
        // all checklists not closed from template tid
        String query1 = "SELECT chkId FROM chklst WHERE chkIsCompleted = FALSE AND chkTempl = ?";

        // ineting tag associated with checklist
        String query2 = "INSERT INTO chk2tag (xtagId, xchkId) VALUES (?, ?)";

        // test if associated tag already exists
        String query3 = "SELECT COUNT(*) FROM chk2tag WHERE xchkId = ? AND xtagId = ?";

        this.tid = Integer.parseInt(par.getPath()[1]);
        this.gid = Integer.parseInt(par.getParams().get("gid"));
        System.out.println("gid: "+gid+"   tid: "+tid);

        this.request = par;

        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2);
        PreparedStatement ps3 = con.prepareStatement(query3);

        try {

            con.setAutoCommit(false);

            ps1.setInt(1, this.tid);
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                int chkId = rs1.getInt("chkId");

                // verify if already exists
                ps3.setInt(1, chkId);
                ps3.setInt(2, this.tid);
                System.out.println(ps3.toString());
                ResultSet rs3 = ps3.executeQuery();
                rs3.next();
                int rowCount = rs3.getInt(1);
                System.out.println(">"+rowCount);

                if(rowCount == 0){  // if doesnt exist insert it
                    System.out.println("chkid: "+ chkId);

                    // then insert the tags
                    ps2.setInt(1,this.tid);
                    ps2.setInt(2,chkId);

                    this.res = ps2.executeUpdate();
                }
            }

        }catch (SQLException e){
            con.rollback();
            throw new AppException("shit happends!"+ e);
        }finally {
            con.commit();
            ps1.close();
            ps2.close();
        }
        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public Object getData() {
        return res;
    }
}
