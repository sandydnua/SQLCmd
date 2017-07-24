package sandy.sqlcmd.model;

import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;

public class UnknownCommand extends Command {
    public UnknownCommand(String[] params){

        super(params);
    }

    @Override
    protected DataSet executeMainProcess() {

        DataSet data = new DataSet("Что Ты ввёл?!");
        data.addString("Введи Help для справки.");
        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {
    }
}
