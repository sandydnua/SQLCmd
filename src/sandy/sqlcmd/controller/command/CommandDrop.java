package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandDrop extends Command {

    public CommandDrop(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorretParametersQuery {
        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[1]);
        String sqlQuery = sqlConstructor.getQueryDrop();
        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Таблица " + params[1] + " удалена");
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(2);
    }
}
