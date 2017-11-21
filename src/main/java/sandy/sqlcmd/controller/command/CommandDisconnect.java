package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandDisconnect extends Command {

    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 1;

    public CommandDisconnect() {
    }

    public CommandDisconnect(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        dbManager.disconnect();
        return new DataSet("Подключение закрыто");
    }

    @Override
    protected void canExecute() throws CantExecuteNoConnectionException {

        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
