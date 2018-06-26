package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandFind extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 2;

    public CommandFind() { }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        if( !dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ){
            throw new MainProcessException( "Нет такой таблицы" );
        }
        DataSet data = dbManager.find(params[INDEX_OF_TABLE_NAME]);
        if( data.quantityRows() <= 1 ){
            data.addString("Таблица пуста. Содержит следующие поля:");
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException {

        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}

