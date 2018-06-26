package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public abstract class Command {

    String[] params;
    DatabaseManager dbManager;

//    protected Command(String[] params){
//        setParams(params);
//    }
    protected Command(){ }

    public void setParams(String[] params){
        this.params = params;
    }

    protected abstract DataSet executeMainProcess() throws Exception;

    public DataSet execute() throws Exception {
        try{
            canExecute();
            return executeMainProcess();
        } catch ( Exception e){
            throw e;
        }
    }
    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    protected abstract void canExecute() throws CantExecuteOrNoConnectionException, MainProcessException;

    void checkConnectAndParameters(int quantity) throws CantExecuteOrNoConnectionException {
        String errorMessages = "";

        try{
            checkConnect();
        }catch (CantExecuteOrNoConnectionException ex){
            errorMessages = ex.getMessage();
        }
        if(params.length != quantity){
            errorMessages += "Неверное число парметров; | ";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteOrNoConnectionException(errorMessages);
    }

    void checkConnectAndMinQuantityParameters(int minQuantity) throws CantExecuteOrNoConnectionException {

        String errorMessages = "";
        try{
            checkConnect();
        }catch (CantExecuteOrNoConnectionException ex){
            errorMessages = ex.getMessage();
        }
        // TODO тут можно сделать элегантнее
        if(params.length < minQuantity){
            errorMessages += "Неверное число парметров; |";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteOrNoConnectionException(errorMessages);

    }

    private void checkConnect() throws CantExecuteOrNoConnectionException {

        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; |";
        }else if( !dbManager.isConnect()){
            errorMessages += "Нет подключения к базе; |";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteOrNoConnectionException(errorMessages);
    }
}
