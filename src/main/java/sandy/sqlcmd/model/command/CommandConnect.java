package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandConnect extends Command {

    private static final int INDEX_ADDRESS_AND_PORT = 1;
    private static final int INDEX_DATABASE_NAME = 2;
    private static final int INDEX_USERNAME = 3;
    private static final int INDEX_PASSWORD = 4;
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 5;

    public CommandConnect(String[] params){
        super(params);
    }
    public CommandConnect(){
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        String address = params[INDEX_ADDRESS_AND_PORT];
        String database = params[INDEX_DATABASE_NAME];
        String userName = params[INDEX_USERNAME];
        String password = params[INDEX_PASSWORD];

        dbManager.connect( address, database, userName, password );
        return new DataSet( "Подключился к базе" );
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException {

        String errorMessages = "";
        if( null == dbManager){
            errorMessages += "Не передан DatabaseManager; ";
        }
        if(params.length != EXPECTED_QUANTITY_OF_PARAMETERS){
            errorMessages += "Неверное число парметров; ";
        }
        if( !"".equals(errorMessages))  throw new CantExecuteOrNoConnectionException(errorMessages);
    }
}
