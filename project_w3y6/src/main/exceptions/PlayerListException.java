package exceptions;

public class PlayerListException extends Exception {

    public PlayerListException() {
        super("Action not allowed with Player List.");
    }
}
