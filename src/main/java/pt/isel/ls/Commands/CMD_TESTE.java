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

/*

description:
comando que dado o parametro git (template id), associa a tag gid a todas as
checklists nao fechadas e criadas a partir da template tid

POST /templates/2/checklists/notclosed/tags gid=17

*/



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
        this.tid = Integer.parseInt(par.getPath()[1]);
        this.gid = Integer.parseInt(par.getParams().get("gid"));
        System.out.println("gid: "+gid+"   tid: "+tid);

        this.request = par;


        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2);

        try {

            con.setAutoCommit(false);

            ps1.setInt(1, this.tid);
            ResultSet rs1 = ps1.executeQuery();
            System.out.println(rs1);

            while(rs1.next()){
                int chkId = rs1.getInt("chkId");
                System.out.println("chkid: "+ chkId);

                // then insert the tags
                ps2.setInt(1,this.tid);
                ps2.setInt(2,chkId);

                this.res = ps2.executeUpdate();
            }

        }catch (SQLException e){
            con.rollback();
            throw new AppException("shit happends! "+ e);
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
