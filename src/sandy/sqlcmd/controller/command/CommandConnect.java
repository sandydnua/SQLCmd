package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandConnect extends Command {

    public CommandConnect(String[] params){
        super(params);
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
    protected void canExecute() throws CantExecuteException {
        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; ";
        }
        if(params.length != 4){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteException(errorMessages);
    }
}
