package sandy.sqlcmd.model.Exceptions;

public class MainProcessException extends Exception  {
    public MainProcessException(String message) {
        super(message);
    }
    public MainProcessException(Exception e) {
        super(e);
    }

}