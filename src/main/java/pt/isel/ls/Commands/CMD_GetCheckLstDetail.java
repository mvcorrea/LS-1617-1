package pt.isel.ls.Commands;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

// command sample: GET /checklists/1

public class CMD_GetCheckLstDetail implements CommandInterface {
    public static String pattern = "(GET /checklists/\\d+)";
    public CheckList cl = new CheckList();

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    // TODO: verify All Exception

    @Override
    public CommandWrapper process(Connection con, RequestParser par) throws SQLException, GenericException {
        String query1 = "SELECT * FROM chklst WHERE chkId = ?";
        String query2 = "SELECT * FROM task WHERE tskChkId = ?";

        int cid = Integer.parseInt(par.getPath()[1]);

        PreparedStatement ps = con.prepareStatement(query1);
        ps.setInt(1, cid);
        ResultSet rs1 = ps.executeQuery();

        if(rs1.next()){
            cl.fill(rs1);
        } else throw new GenericException("checklist ["+ cid +"] not found");

        ps = con.prepareStatement(query2);
        ps.setInt(1, cid);
        ResultSet rs2 = ps.executeQuery();

        while(rs2.next()) cl.addTask(rs2);

        System.out.println(cl.toString());

        ps.close();
        return new CommandWrapper(this);
    }

    public Object getData(){ return cl; }

    @Override
    public boolean validate(RequestParser par) throws GenericException {
        return true;
    }

    @Override
    public String toString(){
        return "GET /checklists/{cid} - returns a checklist with its tasks.\n";
    }
}
