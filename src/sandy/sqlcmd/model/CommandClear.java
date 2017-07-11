package sandy.sqlcmd.model;

public class CommandClear extends Command {
    String sqlQuery = "DELETE FROM ";
    public CommandClear(String[] params) {
        super();
        setParams(params);
    }
    public CommandClear(){

    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessExeption {
        sqlQuery += params[1];
        DataSet data = new DataSet();
        try {
            dbManager.executeUpdate(sqlQuery);
            String strMessage = "Таблица "+params[1]+" очищена";
            data.addString(strMessage);
        } catch (MainProcessExeption mainProcessExepion) {
            throw mainProcessExepion;
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
       checkConnectAndParameters(2);
    }
}
