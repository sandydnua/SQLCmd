package sandy.sqlcmd.controller.command;

import org.springframework.stereotype.Component;
import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

import java.util.*;

public class CommandInsert extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int MIN_QUANTITY_PARAMETERS = 4;
    public CommandInsert(String[] params) {
        super(params);
    }

    public CommandInsert() {
    }

    private String prepareSql() throws IncorrectParametersQuery, MainProcessException {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        List<String> columns = new ArrayList<>();
        sqlConstructor.addTables(params[INDEX_OF_TABLE_NAME]);

        if ( !dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ) {
            throw  new IncorrectParametersQuery( "Нет такой таблицы" );
        }

        for( int i = 3 ; i < params.length; i+=2 ){
            sqlConstructor.addColumnForSelectInsertCreate(params[i-1]);
            columns.add(params[i-1]);
            sqlConstructor.addValuesForInsert(params[i]);
        }

        if( !dbManager.existColumns(params[INDEX_OF_TABLE_NAME], DatabaseManager.FULL_COVERAGES, columns.toArray(new String[]{}) ) ) {
            throw new IncorrectParametersQuery( "Неверный набор полей для вставки");
        }

        return sqlConstructor.getQueryInsert();
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {
        String sqlQuery = prepareSql();

        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Операция прошла успешно");
    }

    @Override
    protected void canExecute() throws CantExecuteException {

        String errorMessage = "";
        try{
            checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);
        }catch (CantExecuteException ex){
            errorMessage +=ex.getMessage();
        }
        if( (params.length % 2) !=0 ){
            errorMessage += "Неверное количество параметров, чего-то не хватает; ";
        }
        if( !"".equals(errorMessage) ){
            throw new CantExecuteException(errorMessage);
        }
    }
}
