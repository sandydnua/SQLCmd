package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public abstract class Command {

    protected String[] params = null;
    protected DatabaseManager dbManager = null;

    public Command(String[] params){
        setParams(params);
    }

    private void setParams(String[] params){
        this.params = params;
    }

    protected abstract DataSet executeMainProcess() throws MainProcessException, IncorretParametersQuery, CompletionOfWorkException;

    public DataSet execute() throws CantExecuteException, IncorretParametersQuery, MainProcessException, CompletionOfWorkException {
        DataSet data = new DataSet();
        canExecute();
        data = executeMainProcess();

        return data;
    }
    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    protected abstract void canExecute() throws CantExecuteException;

    protected void checkConnectAndParameters(int quantity) throws CantExecuteException {
        String errorMessages = "";

        try{
            checkConnect();
        }catch (CantExecuteException ex){
            errorMessages = ex.getMessage();
        }
        if(params.length != quantity){
            errorMessages += "Неверное число парметров; | ";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteException(errorMessages);
    }

    protected void checkConnectAndMinQuantityParameters(int minQuantity) throws CantExecuteException {

        String errorMessages = "";
        try{
            checkConnect();
        }catch (CantExecuteException ex){
            errorMessages = ex.getMessage();
        }
        if(params.length < minQuantity){
            errorMessages += "Неверное число парметров; |";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteException(errorMessages);

    }

    protected void checkConnect() throws CantExecuteException {
        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; |";
        }else if( false == dbManager.isConnect()){
            errorMessages += "Нет подключения к базе; |";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteException(errorMessages);
    }
}
