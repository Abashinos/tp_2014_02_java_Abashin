package exceptions;

public class InvalidDataException extends BadLoginException {
    public InvalidDataException() {
        super("Invalid data input.");
    }
}
