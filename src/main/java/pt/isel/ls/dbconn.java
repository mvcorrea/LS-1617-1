package pt.isel.ls;

// using MySQL here !
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBConn {

    private static MysqlDataSource dataSource = null;

    static {
        dataSource = new MysqlDataSource();
        dataSource.setServerName(System.getenv("LS_DB_HOST"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PASS"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));
    }

    public static MysqlDataSource getDataSource(){
        return dataSource;
    }

}
