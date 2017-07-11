package sandy.sqlcmd.model;

public class CommandDrop extends Command {
    String sqlQuery = "DROP TABLE ";

    public CommandDrop(String[] params) {
        super();
        setParams(params);
    }
    public CommandDrop(){

    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessExeption {
        sqlQuery += params[1];
        dbManager.executeUpdate(sqlQuery);
//        String strMessage = "Таблица "+params[1]+" удалена";
        return new DataSet("Таблица " + params[1] + " удалена");
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(2);
    }
}
