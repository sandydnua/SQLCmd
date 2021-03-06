package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.util.Arrays;

public class CommandCreate extends Command {
    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int FIRST_INDEX_OF_COLUMNS = 2;
    private static final int MIN_QUANTITY_PARAMETERS = 3;

    public CommandCreate() {  }

    @Override
    protected DataSet executeMainProcess() {
        dbManager.createTable(params[INDEX_OF_TABLE_NAME],
                              Arrays.copyOfRange(params,
                                                 FIRST_INDEX_OF_COLUMNS,
                                                 params.length) );
        return new DataSet("Таблица создана");
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException, MainProcessException {
        if ( dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ) {
            throw new MainProcessException("A table with this name already exists!");
        }
        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);
    }

}
