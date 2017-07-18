package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandClear extends Command {
    public CommandClear(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorretParametersQuery {
        DataSet data = new DataSet();
        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[1]);
        String sqlQuery = sqlConstructor.getQueryClear();

        dbManager.executeUpdate(sqlQuery);
        String strMessage = "Таблица "+params[1]+" очищена";
        data.addString(strMessage);

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {
       checkConnectAndParameters(2);
    }
}
