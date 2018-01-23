package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;

public class UnknownCommand extends Command {
    public UnknownCommand(String[] params){

        super(params);
    }

    public UnknownCommand() {
    }

    @Override
    protected DataSet executeMainProcess() {

        DataSet data = new DataSet("Что Ты ввёл?!");
        data.addString("Введи Help для справки.");
        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException {
    }
}
