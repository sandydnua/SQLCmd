package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandFind extends Command {

    private String sqlQuery = "SELECT * FROM ";

    public CommandFind(String[] params){
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        sqlQuery += params[1];
        DataSet data = dbManager.executeQuery(sqlQuery);
        if( data.quantityRows() <= 1 ){
            data.addString("Таблица пуста. Содержит следующие поля:");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndParameters(2);
    }
}

