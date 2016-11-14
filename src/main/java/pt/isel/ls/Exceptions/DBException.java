package pt.isel.ls.Exceptions;


public class DBException extends AppException {
    public DBException(String msg) throws AppException {
        super("DBError: " + msg);
    }
}
