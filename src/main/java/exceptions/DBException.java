package exceptions;

public class DBException extends AccountServiceException {
    public DBException () { super("Database exception caught."); }
    public DBException (String msg) {
        super(msg);
    }
}
