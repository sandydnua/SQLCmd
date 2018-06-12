package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

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

        dbManager.dropTable(params[INDEX_OF_TABLE_NAME]);

        return new DataSet("Таблица " + params[INDEX_OF_TABLE_NAME] + " удалена");
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException {
        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
