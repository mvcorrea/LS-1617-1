package pt.isel.ls.Commands;

import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWriter;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

// command sample: GET /checklists

public class CMD_GetCheckLstDetail implements CommandInterface {
    public static String pattern = "(GET /checklists/\\d+)";
    private int cid = 0;

    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public void process(Connection con, RequestParser par) throws SQLException {
        String query1 = "SELECT * FROM chklst WHERE chkId = %d";
        CommandWriter writer = new CommandWriter(new LinkedList<String>());  //<------ command output class
        PreparedStatement preparedStatement = con.prepareStatement(query1);
        ResultSet rs1 = preparedStatement.executeQuery();
        if(rs1.next()){
            writer.addLine(rs1.getInt("chkId") +"\t|\t"+ rs1.getString("chkName") +"\t|\t"+ rs1.getString("chkDesc") );
        }

//        String query2 = "SELECT * FROM chklst JOIN task ON chklst.chkId = "+ cid;
//        preparedStatement = con.prepareStatement(query2);
//        ResultSet rs2 = preparedStatement.executeQuery();
//        while(rs2.next()) {
//            writer.addLine(rs2.getInt("tskId") +"\t|\t"+ rs2.getString("tskName") +"\t|\t"+ rs2.getString("tskDesc") +"\t|\t"+ rs2.getString("tskDueDate") );
//        }

        writer.print();
        preparedStatement.close();
    }

    @Override
    public boolean validate(RequestParser par) {
        this.cid = Integer.parseInt(par.getPath()[1]);
        return true;
    }

    @Override public String toString(){
        return "GET /checklists/{cid} - returns a checklist with its tasks.\n";
    }
}
