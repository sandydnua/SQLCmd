package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandExit extends Command {

    public CommandExit(String[] params){

        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws CompletionOfWorkException {
        if(null != dbManager && dbManager.isConnect()){
            try {
                dbManager.disconnect();
            } catch (MainProcessException mainProcessExepion) {
                throw new CompletionOfWorkException("Не удалось закрыть соединение с базой! Завершение работы.");
            }
        }
       throw new CompletionOfWorkException("Завершение работы.");
    }

    @Override
    protected void canExecute() throws CantExecuteException {
    }
}
