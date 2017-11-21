package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandDrop extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 2;

    public CommandDrop() {
    }

    public CommandDrop(String[] params) {
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[INDEX_OF_TABLE_NAME]);

        String sqlQuery = sqlConstructor.getQueryDrop();
        dbManager.executeUpdate(sqlQuery);

        return new DataSet("Таблица " + params[INDEX_OF_TABLE_NAME] + " удалена");
    }

    @Override
    protected void canExecute() throws CantExecuteNoConnectionException {
        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
