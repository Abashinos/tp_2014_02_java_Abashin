package exceptions;

public class BadLoginException extends DBException {
    public BadLoginException() {
        super("Login failed");
    }
    public BadLoginException(String msg) {
        super(msg);
    }
}
