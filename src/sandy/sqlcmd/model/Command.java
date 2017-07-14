package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public abstract class Command {

    protected String[] params = null;
    protected DatabaseManager dbManager = null;

    protected abstract DataSet executeMainProcess() throws MainProcessException;
    protected abstract void canExecute() throws CanExecuteException;

    public Command(String[] params){
        setParams(params);
    }

    public void setParams(String[] params){
        this.params = params;
    }
    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    public DataSet execute(){
        DataSet data = new DataSet();;
        try{
            canExecute();
            data = executeMainProcess();
        }catch (CanExecuteException ex){
            String[] strings = ex.getMessage().split("; ");
            data.addString(strings);
        }catch (MainProcessException ex){
            String[] strings = ex.getMessage().split("; ");
            data.addString(strings);
        }
        return data;
    }
    protected void checkConnectAndParameters(int quantity) throws CanExecuteException {
        String errorMessages = "";
        try{
            checkConnect();
        }catch (CanExecuteException ex){
            errorMessages = ex.getMessage();
        }
        if(params.length != quantity){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteException(errorMessages);
    }

    protected void checkConnectAndMinQuantityParameters(int minQuantity) throws CanExecuteException {

        String errorMessages = "";
        try{
            checkConnect();
        }catch (CanExecuteException ex){
            errorMessages = ex.getMessage();
        }
        if(params.length < minQuantity){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteException(errorMessages);

    }
    protected void checkConnect() throws CanExecuteException {
        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; ";
        }else if( false == dbManager.isConnect()){
            errorMessages += "Нет подключения к базе; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteException(errorMessages);
    }
}
