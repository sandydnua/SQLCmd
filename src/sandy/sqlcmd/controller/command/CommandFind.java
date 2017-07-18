package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandFind extends Command {

    public CommandFind(String[] params){
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        String sqlQuery = null;
        try {
            sqlConstructor.addTables(params[1]);
            sqlQuery = sqlConstructor.getQueryFind();
        } catch (IncorretParametersQuery incorretParametersQuery) {
            throw new MainProcessException( incorretParametersQuery.getMessage());
        }
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

