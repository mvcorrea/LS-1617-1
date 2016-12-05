package pt.isel.ls.Exceptions;

public class WEBException extends AppException {
    public WEBException(String msg) throws AppException {
        super("WEBError: " + msg);
    }
}
