package pt.isel.ls.Helpers;

// using MySQL here !
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import pt.isel.ls.Exceptions.GenericException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DBConn {

    private static MysqlDataSource dataSource = null;

    static {
//        dataSource = new MysqlDataSource();
//        dataSource.setServerName(System.getenv("LS_DB_HOST"));
//        dataSource.setUser(System.getenv("LS_DB_USER"));
//        dataSource.setPassword(System.getenv("LS_DB_PASS"));
//        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));

        dataSource = new MysqlDataSource();
        HashMap<String,String> env = null;
        try {
            env = parseEnv();
        } catch (GenericException e) {
            e.printStackTrace();
        }
        System.out.println("connect to DB " + env);
        dataSource.setServerName(env.get("server"));
        dataSource.setUser(env.get("user"));
        dataSource.setPassword(env.get("password"));
        dataSource.setDatabaseName(env.get("database"));
    }

    private static HashMap<String,String> parseEnv() throws GenericException {

        String var = System.getenv("LS_DBCONN_TEST_PSQL");
        if(var == null) throw new GenericException("database env parse error");

        return (HashMap) Arrays.stream(var.split(";")).map(elem -> elem.split("="))
                                                      .filter(elem -> elem.length == 2)
                                                      .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
