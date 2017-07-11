package sandy.sqlcmd.model;

public class CommandExit extends Command {

    public CommandExit(){

    }

    @Override
    protected DataSet executeMainProcess() {
        if(null != dbManager && dbManager.isConnect()){
            try {
                dbManager.disconnect();
            } catch (MainProcessExeption mainProcessExepion) {

            }
        }
        System.exit(0);
        return null;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
    }
}
