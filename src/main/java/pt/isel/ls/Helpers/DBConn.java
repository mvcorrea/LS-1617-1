package pt.isel.ls.Helpers;

// using MySQL here !
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConn {

    private static MysqlDataSource dataSource = null;

    static {
        dataSource = new MysqlDataSource();
        dataSource.setServerName(System.getenv("LS_DB_HOST"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PASS"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));
    }

    public static Connection getDataSource() throws SQLException {
        return dataSource.getConnection();
    }

}
