package sandy.sqlcmd.model;

public class UnknownCommnad extends Command {
    public UnknownCommnad(){

    }
    @Override
    public void setParams(String[] params) {

    }

    @Override
    protected DataSet executeMainProcess() {
        DataSet data = new DataSet();
        data.addString("Что Ты ввёл?");
        data.addString("Введи Help для справки");
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
    }
}
