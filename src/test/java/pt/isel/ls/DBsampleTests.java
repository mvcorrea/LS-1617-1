package pt.isel.ls;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class DBsampleTests {

    /* ???? HOW TO AUTO SETUP SAMPLE DATABASE BEFORE RUNNING TESTS  ???? RUN A BASH SCRIPT ???? */

    static int alId = 0;    // <---- o id eh afectado no insert (todos os demais testes tem por dependencia o primeiro)
                            // como criar dependencias entre os testes ????
    static String name = "Chico Sumido";
    static int number  = 23456;

    // test insert
    @Test
    public void testDB_Ins() throws SQLException {
        DBConn db = new DBConn();
        Connection dbconn = db.getDataSource().getConnection();

        // insert
        alId = DBsample.testInsert(dbconn, name, number);
        assertTrue(alId > 0);

        // select
        String qry1 = "SELECT * FROM Alunos WHERE alId = "+ alId;
        LinkedList<String[]> rows1 = DBsample.testSelect(dbconn, qry1);
        assertTrue(Integer.parseInt(rows1.get(0)[1]) == number);
        assertTrue(rows1.get(0)[2].equals(name));

        dbconn.close();
    }

    // test update
    @Test
    public void testDB_Upd() throws SQLException {
        DBConn db = new DBConn();
        Connection dbconn = db.getDataSource().getConnection();

        // update
        number += 100;
        DBsample.testUpdate(dbconn, "Alunos", "alNumber", alId, number);

        // select
        String qry1 = "SELECT * FROM Alunos WHERE alId = "+ alId;
        LinkedList<String[]> rows2 = DBsample.testSelect(dbconn, qry1);
        assertTrue(Integer.parseInt(rows2.get(0)[1]) == number);

        dbconn.close();
    }

    // test delete
    @Test
    public void testDB_Del() throws SQLException {
        DBConn db = new DBConn();
        Connection dbconn = db.getDataSource().getConnection();

        // delete
        DBsample.testDelete(dbconn, alId);

        // select
        String qry1 = "SELECT * FROM Alunos WHERE alId = "+ alId;
        LinkedList<String[]> rows2 = DBsample.testSelect(dbconn, qry1);
        assertTrue(rows2.size() == 0);

        dbconn.close();
    }

}
