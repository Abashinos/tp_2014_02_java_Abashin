package exceptions;

public class InvalidDataException extends BadLoginException {
    public InvalidDataException() {
        super("Invalid login or password");
    }
}
