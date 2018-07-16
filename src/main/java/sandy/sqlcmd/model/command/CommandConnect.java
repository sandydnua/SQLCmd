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

    public CommandConnect(){ }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        dbManager.connect( params[INDEX_ADDRESS_AND_PORT],
                           params[INDEX_DATABASE_NAME],
                           params[INDEX_USERNAME],
                           params[INDEX_PASSWORD]);
        return new DataSet( "Connected to the database" );
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
