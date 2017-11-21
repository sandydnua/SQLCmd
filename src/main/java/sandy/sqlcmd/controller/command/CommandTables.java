package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandTables extends Command {

    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 1;

    public CommandTables(String[] params) {
        super(params);
    }

    public CommandTables() {
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        String sql = sqlConstructor.getQueryTables();
        return dbManager.executeQuery(sql);
    }

    @Override
    protected void canExecute() throws CantExecuteNoConnectionException {

        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
