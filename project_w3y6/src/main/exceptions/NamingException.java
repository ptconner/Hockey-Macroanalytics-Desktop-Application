package exceptions;

public class NamingException extends Exception {

    public NamingException() {
        super("Name cannot be empty");
    }
}
