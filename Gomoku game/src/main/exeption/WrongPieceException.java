package exeption;

public class WrongPieceException extends Exception {
    public WrongPieceException() {
        super("input the incorrect piece value");
    }
}
