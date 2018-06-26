package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.util.*;

public class CommandInsert extends Command {

    private Map<String, String> newRecord = new HashMap();
    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int MIN_QUANTITY_PARAMETERS = 4;

    public CommandInsert() { }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        dbManager.insert(params[INDEX_OF_TABLE_NAME], newRecord);

        return new DataSet("Операция прошла успешно");
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException, MainProcessException {

        String errorMessage = "";
        try{
            checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);
        }catch (CantExecuteOrNoConnectionException ex){
            errorMessage +=ex.getMessage();
        }
        if( (params.length % 2) !=0 ){
            errorMessage += "Неверное количество параметров, чего-то не хватает; ";
        }
        if ( !dbManager.existTable(params[INDEX_OF_TABLE_NAME])) {
            errorMessage += "Нет такой таблицы";
        }
        for( int i = 3 ; i < params.length; i+=2 ){
            newRecord.put(params[i-1], params[i]);
        }

        if( !dbManager.existColumns(params[INDEX_OF_TABLE_NAME], DatabaseManager.FULL_COVERAGES, newRecord.keySet() ) ) {
            errorMessage += "Неверный набор полей для вставки";
        }
        if( !"".equals(errorMessage) ){
            throw new CantExecuteOrNoConnectionException(errorMessage);
        }
    }
}
