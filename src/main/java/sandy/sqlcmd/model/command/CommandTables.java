package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandTables extends Command {

    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 1;

    public CommandTables() { }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        return dbManager.getTables();
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException {

        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
