package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandClear extends Command {
    String sqlQuery = "DELETE FROM ";
    public CommandClear(String[] params) {
        super();
        setParams(params);
    }

    @Override
    protected DataSet mainProcess() throws MainProcessExepion {
        sqlQuery += params[1];
        DataSet data = new DataSet();
        try {
            dbManager.executeUpdate(sqlQuery);
            String strMessage = "Таблица "+params[1]+" очищена";
            data.addString(strMessage);
        } catch (MainProcessExepion mainProcessExepion) {
            throw mainProcessExepion;
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
       checkConnectAndParameters(2);
    }
}
