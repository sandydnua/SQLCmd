package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandTables extends Command {

    private final String sqlQuery = "select table_name " +
                                    "from information_schema.tables " +
                                    "where table_schema='public'";

    public CommandTables(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        return dbManager.executeQuery(sqlQuery);
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(1);
    }
}
