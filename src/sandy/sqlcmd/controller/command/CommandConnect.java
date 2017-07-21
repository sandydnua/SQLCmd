package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandConnect extends Command {

    private static final int INDEX_DATABASE_NAME = 1;
    private static final int INDEX_USERNAME = 2;
    private static final int INDEX_PASSWORD = 3;

    public CommandConnect(String[] params){
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        String database = params[INDEX_DATABASE_NAME];
        String userName = params[INDEX_USERNAME];
        String password = params[INDEX_PASSWORD];

        dbManager.connect( database, userName, password );
        return new DataSet( "Подключился к базе" );
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
