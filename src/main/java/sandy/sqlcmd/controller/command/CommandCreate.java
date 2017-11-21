package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

import java.util.Arrays;

public class CommandCreate extends Command {
    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int FIRST_INDEX_OF_COLUMNS = 2;
    private static final int MIN_QUANTITY_PARAMETERS = 3;

    public CommandCreate(String[] params) {
        super(params);
    }
    public CommandCreate() {
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[INDEX_OF_TABLE_NAME]);
        sqlConstructor.addColumnForSelectInsertCreate( Arrays.copyOfRange( params, FIRST_INDEX_OF_COLUMNS, params.length));

        String sqlQuery = sqlConstructor.getQueryCreateTable();
        dbManager.executeUpdate( sqlQuery );

        return new DataSet("Таблица создана");
    }

    @Override
    protected void canExecute() throws CantExecuteNoConnectionException {

        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);
    }

}
