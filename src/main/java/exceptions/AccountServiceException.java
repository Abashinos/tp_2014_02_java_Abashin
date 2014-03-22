package exceptions;

public class AccountServiceException extends Exception {
    public AccountServiceException () { super("Account is not recognized"); }
    public AccountServiceException (String msg) { super(msg); }
}
