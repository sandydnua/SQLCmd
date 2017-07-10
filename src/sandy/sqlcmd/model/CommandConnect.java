package sandy.sqlcmd.model;

public class CommandConnect extends Command {

    public CommandConnect(String[] params){
        setParams(params);
    }
    public CommandConnect(){

    }
    @Override
    protected DataSet mainProcess() throws CommandUpdate.MainProcessExepion {
        String database = params[1];
        String userName = params[2];
        String password = params[3];
        dbManager.connect(database,userName,password);
        return new DataSet("Подключился к базе");
    }
    @Override
    protected void canExecute() throws CanExecuteExeption{
        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; ";
        }
        if(params.length != 4){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteExeption(errorMessages);
    }
}
