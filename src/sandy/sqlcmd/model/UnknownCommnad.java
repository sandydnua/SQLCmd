package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;

public class UnknownCommnad extends Command {
    public UnknownCommnad(String[] params){
        super(params);

    }

    @Override
    protected DataSet executeMainProcess() {
        DataSet data = new DataSet();
        data.addString("Что Ты ввёл?");
        data.addString("Введи Help для справки");
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteException {
    }
}
