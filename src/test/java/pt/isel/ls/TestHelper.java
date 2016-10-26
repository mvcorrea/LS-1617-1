package pt.isel.ls;


import pt.isel.ls.Helpers.DBConn;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static String[] commd = {"sh", "-c", "mysql -u dbuser -pdbuser < docs/dbTest.sql"};
    public static Connection conn;
    public static Process proc;

    public TestHelper() throws RuntimeException {
        try {
            this.conn = new DBConn().getConnection();
            this.proc = Runtime.getRuntime().exec(commd);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public Timestamp str2ts(String datetime) throws ParseException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd+hhmm");  // MUST USE THIS FORMAT
        java.util.Date parsedDate = dateFormat.parse(datetime);
        return new java.sql.Timestamp(parsedDate.getTime());
    }

    public static void set(Map<String, String> newenv) throws Exception {
        Class[] classes = Collections.class.getDeclaredClasses();
        Map<String, String> env = System.getenv();
        for(Class cl : classes) {
            if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                Field field = cl.getDeclaredField("m");
                field.setAccessible(true);
                Object obj = field.get(env);
                Map<String, String> map = (Map<String, String>) obj;
                map.clear();
                map.putAll(newenv);
            }
        }
    }

    public static void setEnv() throws Exception {
        HashMap<String, String> env = new HashMap<>();
        env.put("LS_DB_HOST", "127.0.0.1");
        env.put("LS_DB_USER", "dbuser");
        env.put("LS_DB_PASS", "dbuser");
        env.put("LS_DB_NAME", "sampleDB");
        set(env);
    }
}
