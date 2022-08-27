package exceptions;

public class EmptyException extends Exception {

    public EmptyException() {
        super("Team is empty.");
    }
}
