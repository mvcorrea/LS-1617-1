package pt.isel.ls;

import pt.isel.ls.Exceptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DBsample {


    public static LinkedList<String[]> testSelect(Connection con, String qry) throws SQLException, GenericExeption {
        LinkedList<String[]> sqlData = new LinkedList<>();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(qry);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] data = new String[]{   String.valueOf(rs.getInt("alId")),
                                                String.valueOf(rs.getInt("alNumber")),
                                                rs.getString("alName")};
                sqlData.add( data );
            }

            //if(sqlData.size() == 0 ) throw new GenericException("sql query with no return");  // TESTING

        } catch (SQLException e) {
            //System.out.println(e);  // TODO: ! NOT TO BE HERE ! REPEAT ON OTHERS !!
            throw new SQLException("bla, bla, bla");
        }
        return sqlData;
    }


    public static int testInsert(Connection con, String name, int number){
        String qry = "INSERT INTO Alunos (alNumber, alName) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(qry, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, number);
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int alId = rs.next() ? rs.getInt(1) : 0;
            return alId;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }


    public static void testUpdate(Connection con, String tbl, String fld, int id, int number) {
        String qry = "UPDATE TBL SET FLD = ? WHERE alId = ?";
        qry = qry.replaceAll("TBL", tbl);
        qry = qry.replaceAll("FLD", fld);

        try {
            PreparedStatement preparedStatement = con.prepareStatement(qry);
            preparedStatement.setInt(1, number);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    public static void testDelete(Connection con, int id) {
        String qry = "DELETE FROM Alunos WHERE alId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(qry);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
