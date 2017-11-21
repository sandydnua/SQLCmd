package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public abstract class Command {

    String[] params;
    DatabaseManager dbManager;

    protected Command(String[] params){
        setParams(params);
    }
    protected Command(){
    }

    public void setParams(String[] params){
        this.params = params;
    }

    protected abstract DataSet executeMainProcess() throws Exception;

    public DataSet execute() throws Exception {
        DataSet data;
        canExecute();
        data = executeMainProcess();

        return data;
    }
    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    protected abstract void canExecute() throws CantExecuteNoConnectionException, MainProcessException;

    void checkConnectAndParameters(int quantity) throws CantExecuteNoConnectionException {
        String errorMessages = "";

        try{
            checkConnect();
        }catch (CantExecuteNoConnectionException ex){
            errorMessages = ex.getMessage();
        }
        if(params.length != quantity){
            errorMessages += "Неверное число парметров; | ";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteNoConnectionException(errorMessages);
    }

    void checkConnectAndMinQuantityParameters(int minQuantity) throws CantExecuteNoConnectionException {

        String errorMessages = "";
        try{
            checkConnect();
        }catch (CantExecuteNoConnectionException ex){
            errorMessages = ex.getMessage();
        }
        // TODO тут можно сделать элегантнее
        if(params.length < minQuantity){
            errorMessages += "Неверное число парметров; |";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteNoConnectionException(errorMessages);

    }

    private void checkConnect() throws CantExecuteNoConnectionException {

        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; |";
        }else if( !dbManager.isConnect()){
            errorMessages += "Нет подключения к базе; |";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteNoConnectionException(errorMessages);
    }
}
