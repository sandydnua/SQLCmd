package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
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
//        String strMessage = "Таблица "+params[1]+" удалена";
        return new DataSet("Таблица " + params[1] + " удалена");
    }

    @Override
    protected void canExecute() throws CanExecuteException {
        checkConnectAndParameters(2);
    }
}
