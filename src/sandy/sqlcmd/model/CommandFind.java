package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandFind extends Command {

    private String sqlQuery = "select * from ";

    public CommandFind(String[] params){
        setParams(params);
    }

    @Override
    protected DataSet mainProcess() throws MainProcessExepion {
        sqlQuery += params[1];
        DataSet data = dbManager.executeQuery(sqlQuery);
        if( data.getSizeTable() <= 1 ){
            data.addString("Таблица пуста. Содержит следующие поля");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(2);
    }
}

