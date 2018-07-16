package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public abstract class Command {

    String[] params;
    DatabaseManager dbManager;

    protected Command(){ }

    public void setParams(String[] params){
        this.params = params;
    }

    protected abstract DataSet executeMainProcess() throws Exception;

    public DataSet execute() throws MainProcessException {
        try{
            canExecute();
            return executeMainProcess();
        } catch ( Exception e){
            throw new MainProcessException(e.getMessage());
        }
    }
    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    protected abstract void canExecute() throws CantExecuteOrNoConnectionException, MainProcessException;

    protected void checkConnectAndParameters(int quantity) throws CantExecuteOrNoConnectionException {
        checkConnect();
        if(params.length != quantity){
            throw new CantExecuteOrNoConnectionException("Incorrect number of parameters!");
        }
    }

    protected void checkConnectAndMinQuantityParameters(int minQuantity) throws CantExecuteOrNoConnectionException {
        checkConnect();
        if(params.length < minQuantity){
            throw new CantExecuteOrNoConnectionException("Incorrect number of parameters!");
        }
    }

    private void checkConnect() throws CantExecuteOrNoConnectionException {
        if( null == dbManager){
           String errorMessages = "DatabaseManager does not exist. No connection to the database!";
           throw new CantExecuteOrNoConnectionException(errorMessages);
        }else if( !dbManager.isConnect()){
            String errorMessages = "No connection to the database!";
           throw new CantExecuteOrNoConnectionException(errorMessages);
        }
    }
}
