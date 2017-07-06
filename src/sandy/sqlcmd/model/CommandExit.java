package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandExit extends Command {

    public CommandExit(){

    }

    @Override
    protected DataSet mainProcess() {
        if(null != dbManager && dbManager.isConnect()){
            try {
                dbManager.disconnect();
            } catch (MainProcessExepion mainProcessExepion) {

            }
        }
        System.exit(0);
        return null;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
    }
}
