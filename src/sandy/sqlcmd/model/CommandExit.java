package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandExit extends Command {

    public CommandExit(String[] params){
        super(params);

    }

    @Override
    protected DataSet executeMainProcess() {
        if(null != dbManager && dbManager.isConnect()){
            try {
                dbManager.disconnect();
            } catch (MainProcessException mainProcessExepion) {

            }
        }
        throw new CompletionOfWorkException("Завершение работы.");
    }

    @Override
    protected void canExecute() throws CanExecuteException {
    }
}
