package sandy.sqlcmd.model;

public class CommandFind extends Command {

    private String sqlQuery = "select * from ";

    public CommandFind(String[] params){
        setParams(params);
    }
    public CommandFind(){

    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessExeption {
        sqlQuery += params[1];
        DataSet data = dbManager.executeQuery(sqlQuery);
        if( data.quantityRows() <= 1 ){
            data.addString("Таблица пуста. Содержит следующие поля");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(2);
    }
}

