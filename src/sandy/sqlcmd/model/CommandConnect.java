package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandConnect extends Command {

    public CommandConnect(String[] params){
        super(params);
        //        setParams(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        String database = params[1];
        String userName = params[2];
        String password = params[3];
        dbManager.connect(database,userName,password);
        return new DataSet("Подключился к базе");
    }
    @Override
    protected void canExecute() throws CanExecuteException {
        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; ";
        }
        if(params.length != 4){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CanExecuteException(errorMessages);
    }
}
