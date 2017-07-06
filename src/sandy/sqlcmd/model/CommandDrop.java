package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandDrop extends Command {
    String sqlQuery = "DROP TABLE ";

    public CommandDrop(String[] params) {
        super();
        setParams(params);
    }

    @Override
    protected DataSet mainProcess() throws MainProcessExepion {
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
