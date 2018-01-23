package sandy.sqlcmd.model.Exceptions;


public class CantExecuteOrNoConnectionException extends Exception {
    public CantExecuteOrNoConnectionException(String message) {
        super(message);
    }

}
