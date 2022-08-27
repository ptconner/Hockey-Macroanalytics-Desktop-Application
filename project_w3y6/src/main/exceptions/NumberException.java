package exceptions;

public class NumberException extends Exception {

    public NumberException() {
        super("Numbers must be greater than zero.");
    }
}
