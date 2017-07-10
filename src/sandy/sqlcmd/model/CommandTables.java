package sandy.sqlcmd.model;

public class CommandTables extends Command {

    private final String sqlQuery = "select table_name " +
                                    "from information_schema.tables " +
                                    "where table_schema='public'";

    public CommandTables(String[] params) {
        setParams(params);
    }
    public CommandTables(){

    }
    @Override
    protected DataSet mainProcess() throws CommandUpdate.MainProcessExepion {
        return dbManager.executeQuery(sqlQuery);
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(1);
    }
}
