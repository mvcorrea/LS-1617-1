package pt.isel.ls.Helpers;

// using MySQL here !
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import pt.isel.ls.Debug;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
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
        } catch (AppException e) {
            e.printStackTrace();
        }
        if(Debug.ON) System.out.println("connect to DB " + env);
        try {
            String srvName = InetAddress.getByName(env.get("server")).getHostAddress().toString();
            //System.out.println(srvName);
            dataSource.setServerName(srvName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        dataSource.setPort(3306);
        dataSource.setUser(env.get("user"));
        dataSource.setPassword(env.get("password"));
        dataSource.setDatabaseName(env.get("database"));
        //System.out.println(env.get("database"));
    }

    private static HashMap<String,String> parseEnv() throws AppException {

        String var = System.getenv("LS_DBCONN_TEST_PSQL");
        if(var == null) throw new AppException("database env parse error");

        return (HashMap) Arrays.stream(var.split(";")).map(elem -> elem.split("="))
                                                      .filter(elem -> elem.length == 2)
                                                      .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    public static Connection getConnection() throws Exception {
        try {
            return dataSource.getConnection();
        } catch (Exception e){
            throw new Exception("getConnection: "+ e.getMessage() );
        }

    }

}
