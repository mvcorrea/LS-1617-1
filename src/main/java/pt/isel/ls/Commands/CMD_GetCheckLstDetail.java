package pt.isel.ls.Commands;

import pt.isel.ls.Containers.CheckList;
import pt.isel.ls.Containers.Task;
import pt.isel.ls.Exceptions.GenericException;
import pt.isel.ls.Helpers.CommandInterface;
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
    public void process(Connection con, RequestParser par) throws SQLException, GenericException {
        String query1 = "SELECT * FROM chklst WHERE chkId = ?";
        String query2 = "SELECT * FROM task WHERE tskChkId = ?";

        this.cid = Integer.parseInt(par.getPath()[1]);   // not here

        // LinkedList<Task> tasks = new LinkedList<>();
        PreparedStatement preparedStatement = con.prepareStatement(query1);
        preparedStatement.setInt(1, cid);
        ResultSet rs1 = preparedStatement.executeQuery();

        CheckList cl = new CheckList();
        if(rs1.next()){
            cl.add(rs1);
        } else throw new GenericException("checklist ["+ cid +"] not found");

        preparedStatement = con.prepareStatement(query2);
        preparedStatement.setInt(1, cid);
        ResultSet rs2 = preparedStatement.executeQuery();

        while(rs2.next()) cl.addTask(rs2);

//        String query2 = "SELECT * FROM task WHERE tskChkId = ?";
//        preparedStatement = con.prepareStatement(query2);
//        preparedStatement.setInt(1, cid);
//        ResultSet rs2 = preparedStatement.executeQuery();
        System.out.println(cl.toString());
        //System.out.println(cl.numTasks());


        // TODO: create a container for each type


//        // trying to create an auto populate method from a db resultset
//        // this method should recieve an object type (to be the list type)
//        int numcols = rs2.getMetaData().getColumnCount();
//        LinkedList<LinkedList<String>> result = new LinkedList<>();
//        while(rs2.next()){
//            LinkedList<String> row = new LinkedList<>(); // obj list
//            for(int i = 1; i <= numcols; i++){
//                row.add(resultset.getString(i));
//            }
//            result.add(row);
//        }



//        while(rs2.next()) {
//            writer.addLine(rs2.getInt("tskId") +"\t|\t"+ rs2.getString("tskName") +"\t|\t"+ rs2.getString("tskDesc") +"\t|\t"+ rs2.getString("tskDueDate") );
//        }
//
//        writer.print();
        preparedStatement.close();
    }

    @Override
    public boolean validate(RequestParser par) {
        //this.cid = Integer.parseInt(par.getPath()[1]);
        return true;
    }

    @Override public String toString(){
        return "GET /checklists/{cid} - returns a checklist with its tasks.\n";
    }
}
