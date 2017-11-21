package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandClear extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 2;

    public CommandClear(String[] params) {
        super(params);
    }
    public CommandClear() {
    }


    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {


        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[INDEX_OF_TABLE_NAME]);

        String sqlQuery = sqlConstructor.getQueryClear();
        dbManager.executeUpdate(sqlQuery);

        String strMessage = "Таблица "+params[INDEX_OF_TABLE_NAME]+" очищена";

        return new DataSet(strMessage);
    }

    @Override
    protected void canExecute() throws CantExecuteNoConnectionException {

       checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
