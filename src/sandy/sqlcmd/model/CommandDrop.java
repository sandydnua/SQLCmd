package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandDrop extends Command {
    String sqlQuery = "DROP TABLE ";

    public CommandDrop(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        sqlQuery += params[1];
        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Таблица " + params[1] + " удалена");
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(2);
    }
}
