package sandy.sqlcmd.model;

public abstract class Command {

    protected String[] params = null;
    protected DatabaseManager dbManager = null;

    protected abstract DataSet executeMainProcess() throws MainProcessExeption;
    protected abstract void canExecute() throws CanExecuteExeption;

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
        }catch (CanExecuteExeption ex){
            String[] strings = ex.getMessage().split("; ");
            data.addString(strings);
        }catch (MainProcessExeption ex){
            String[] strings = ex.getMessage().split("; ");
            data.addString(strings);
        }
        return data;
    }
    protected void checkConnectAndParameters(int quantity) throws CanExecuteExeption {
        String errorMessages = "";
        try{
            checkConnect();
        }catch (CanExecuteExeption ex){
            errorMessages = ex.getMessage();
        }
        if(params.length != quantity){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteExeption(errorMessages);
    }

    protected void checkConnectAndMinQuantityParameters(int minQuantity) throws CanExecuteExeption {

        String errorMessages = "";
        try{
            checkConnect();
        }catch (CanExecuteExeption ex){
            errorMessages = ex.getMessage();
        }
        if(params.length < minQuantity){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteExeption(errorMessages);

    }
    protected void checkConnect() throws CanExecuteExeption {
        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; ";
        }else if( false == dbManager.isConnect()){
            errorMessages += "Нет подключения к базе; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteExeption(errorMessages);
    }
}
