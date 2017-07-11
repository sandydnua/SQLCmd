package sandy.sqlcmd.model;

public class CommandDisonnect extends Command {
    public CommandDisonnect(String[] params) {
        super();
        setParams(params);
    }
    public CommandDisonnect(){

    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessExeption {
        dbManager.disconnect();
        return new DataSet("Подкючение закрыто");
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(2);
    }
}
