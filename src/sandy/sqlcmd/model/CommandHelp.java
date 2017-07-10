package sandy.sqlcmd.model;

public class CommandHelp extends Command {
    DataSet data;
    public CommandHelp(){

    }
    @Override
    public void setParams(String[] params) {
        DataSet data = new DataSet();
        data.addString("Команда HELP отработала");
        data.addString("Что Ты еще хочешь?");
    }

    @Override
    protected DataSet mainProcess() {
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
    }


}
