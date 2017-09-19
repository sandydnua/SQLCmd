package sandy.sqlcmd.controller.command;

import org.springframework.stereotype.Component;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandFind extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 2;

    public CommandFind() {
    }

    public CommandFind(String[] params){
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        String sqlQuery;

        sqlConstructor.addTables(params[1]);
        sqlQuery = sqlConstructor.getQueryFind();

        if( !dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ){
            throw new MainProcessException( "Нет такой таблицы" );
        }

        DataSet data = dbManager.executeQuery(sqlQuery);
        if( data.quantityRows() <= 1 ){
            data.addString("Таблица пуста. Содержит следующие поля:");
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {

        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}

