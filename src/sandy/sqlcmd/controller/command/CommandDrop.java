package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandDrop extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 2;

    public CommandDrop(String[] params) {
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorretParametersQuery {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[INDEX_OF_TABLE_NAME]);

        String sqlQuery = sqlConstructor.getQueryDrop();
        dbManager.executeUpdate(sqlQuery);

        return new DataSet("Таблица " + params[INDEX_OF_TABLE_NAME] + " удалена");
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
