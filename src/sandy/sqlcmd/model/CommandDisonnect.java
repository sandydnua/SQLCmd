package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandDisonnect extends Command {
    public CommandDisonnect(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        dbManager.disconnect();
        return new DataSet("Подкючение закрыто");
    }

    @Override
    protected void canExecute() throws CanExecuteException {
        checkConnectAndParameters(2);
    }
}
