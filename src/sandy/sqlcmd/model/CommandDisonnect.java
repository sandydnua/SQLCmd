package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandDisonnect extends Command {
    public CommandDisonnect(String[] params) {
        super();
        setParams(params);
    }

    @Override
    protected DataSet mainProcess() throws MainProcessExepion {
        dbManager.disconnect();
        return new DataSet("Подкючение закрыто");
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(2);
    }
}
