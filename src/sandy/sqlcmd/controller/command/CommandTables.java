package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandTables extends Command {


    public CommandTables(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        String sql = sqlConstructor.getQueryTables();
        return dbManager.executeQuery(sql);
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(1);
    }
}
