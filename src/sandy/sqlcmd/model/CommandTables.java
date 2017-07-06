package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandTables extends Command {

    private final String sqlQuery = "select table_name " +
                                    "from information_schema.tables " +
                                    "where table_schema='public'";

    public CommandTables(String[] params) {
        setParams(params);
    }

    @Override
    protected DataSet mainProcess() throws MainProcessExepion {
        return dbManager.executeQuery(sqlQuery);
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(1);
    }
}
