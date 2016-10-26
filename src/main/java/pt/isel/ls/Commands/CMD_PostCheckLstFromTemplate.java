package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

// command sample: POST /templates/3/tasks name=Graça&description="Miradouro da Graça"

public class CMD_PostCheckLstFromTemplate implements CommandInterface {
    public static String pattern = "(POST /templates/\\d+/create)";
    int temId, tskId, chkId;
    boolean chkIsCompleted;
    Timestamp chkDueDate, tskDueDate;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    // TODO: verify All Exception && USE Transactions (setAutoCommit) !!!

    @Override
    public Object process(Connection con, RequestParser par) throws SQLException, ParseException {

        String query1 = "SELECT * FROM templ WHERE temId = ?";
        String query2 = "INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES (?, ?, ?)";
        String query3 = "INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc, tskChkId)\n" +
                        "  (SELECT tskTemId, tskOrder, tskName, tskDesc, ? FROM task WHERE tskTemId = ?)";

        this.temId = Integer.parseInt(par.getPath()[1]);

        PreparedStatement ps1 = con.prepareStatement(query1);
        ps1.setInt(1, temId);
        ResultSet rs1 = ps1.executeQuery();

        if(rs1.next()){  // template exists

            System.out.println("template name> "+rs1.getString("temName"));

            // create the checklist if params not given maintain template original
            PreparedStatement ps2 = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
            ps2.setString(1, par.getParams().get("name") != null ?par.getParams().get("name") : rs1.getString("temName"));
            ps2.setString(2, par.getParams().get("description") != null ? par.getParams().get("description") : rs1.getString("temDesc"));
            ps2.setTimestamp(3, par.getParams().get("dueDate") != null ? str2ts(par.getParams().get("dueDate")) : null);
            ps2.execute();
            ResultSet rs2 = ps2.getGeneratedKeys();
            this.chkId = rs2.next() ? rs2.getInt(1) : 0;

            System.out.println("checklist number> "+this.chkId);

            // populate checklist with the tasks
            PreparedStatement ps3 = con.prepareStatement(query3, PreparedStatement.RETURN_GENERATED_KEYS);
            ps3.setInt(1, tskId);
            ps3.setInt(2, temId);
            ps3.execute();
            ResultSet rs3 = ps3.getGeneratedKeys();

            System.out.println("created Checklist with id: "+ chkId +" from template: "+ temId);

            ps2.close();
            ps3.close();

        } else System.out.println("unable to find template with id: "+temId);

        ps1.close();
        return null;
    }

    // NOT TO BE HERE, USED IN MANY CLASSES :)
    public Timestamp str2ts(String datetime) throws ParseException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd+hhmm");
        java.util.Date parsedDate = dateFormat.parse(datetime);
        return new java.sql.Timestamp(parsedDate.getTime());
    }


    @Override
    public boolean validate(RequestParser par) throws ParseException {
        return false;
    }

    @Override public String toString(){
        return "POST /templates/{tid}/create - creates a new checklist with the tasks of the template tid.\n";
    }
}
