package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandClear extends Command {
    String sqlQuery = "DELETE FROM ";
    public CommandClear(String[] params) {
        super(params);
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        sqlQuery += params[1];
        DataSet data = new DataSet();
        try {
            dbManager.executeUpdate(sqlQuery);
            String strMessage = "Таблица "+params[1]+" очищена";
            data.addString(strMessage);
        } catch (MainProcessException mainProcessExepion) {
            throw mainProcessExepion;
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteException {
       checkConnectAndParameters(2);
    }
}
