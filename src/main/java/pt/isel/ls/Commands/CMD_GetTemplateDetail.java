package pt.isel.ls.Commands;

import pt.isel.ls.Containers.Template;
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

// command sample: GET /templates

public class CMD_GetTemplateDetail implements CommandInterface {
    public static String pattern = "(GET /templates/\\d+)";
    public RequestParser request;
    public Template tp = new Template();

    @Override
    public RequestParser getRequest() { return request; }

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, AppException {
        // get the template
        String query1 = "SELECT * FROM templ WHERE temId = ?";

        // get template tasks
        String query2 = "SELECT * FROM\n" +
                "  (SELECT * FROM task WHERE tskTemId = ? AND tskChkId IS NOT NULL) AS X\n" +
                "  JOIN\n" +
                "  (SELECT * FROM templ) AS Y\n" +
                "    ON X.tskTemId = Y.temId;\n";

        // get checklists created from the template
        String query3 = "SELECT * FROM chklst WHERE chkTempl = ?";

        this.request = par;
        int tid = Integer.parseInt(par.getPath()[1]);

        try {

            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setInt(1, tid);
            ResultSet rs1 = ps1.executeQuery();

            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, tid);
            ResultSet rs2 = ps2.executeQuery();

            PreparedStatement ps3 = con.prepareStatement(query3);
            ps3.setInt(1, tid);
            ResultSet rs3 = ps3.executeQuery();

            if(rs1.next()){
                tp.fill(rs1);
            } else throw new DBException("checklist ["+ tid +"] not found");

            while(rs2.next()) tp.addTask(rs2);  // add all tasks
            while(rs3.next()) tp.addChecklist(rs3); // add checklists

            //System.out.println(tp.toString());

            ps1.close();
            ps2.close();
            ps3.close();


        } catch (SQLException e){ throw new DBException( e.getMessage() ); };

        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException {
        return false;
    }

    @Override
    public String toString(){
        return "GET /templates/{tid} - returns the details for the template tid, including its tasks and the checklists created from it\n";
    }

    @Override
    public Object getData() {
        return tp;
    }
}
