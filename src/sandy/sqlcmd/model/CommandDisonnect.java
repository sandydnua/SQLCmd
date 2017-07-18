package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandDisonnect extends Command {
    public CommandDisonnect(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        dbManager.disconnect();
        return new DataSet("Подключение закрыто");
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(1);
    }
}
