package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandUpdate extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int INDEX_OF_COLUMN_FOR_WHERE = 2;
    private static final int MIN_QUANTITY_PARAMETERS = 6;

    public CommandUpdate(String[] params) {
        super(params);
    }

    public CommandUpdate() {
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {


        String tableName = params[INDEX_OF_TABLE_NAME];
        Map<String, String> condition = new HashMap();
        Map<String, String> newValue = new HashMap();

        for (int i = INDEX_OF_COLUMN_FOR_WHERE; i < params.length; i = i + 4 ) {
            condition.put(params[i],params[i+1]);
            newValue.put(params[i+2],params[i+3]);
        }

        DataSet data = dbManager.find(tableName, condition);

        if (data.quantityRows() > 1) {

            dbManager.update(tableName, condition, newValue);
            data.addString("Эти строки будут обновлены");
        } else {
            data = new DataSet("С такими параметрами строки не найдены.");
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException, MainProcessException {

        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);

        if ( (params.length - 2) % 4 != 0) {
            throw new CantExecuteOrNoConnectionException("Неверное число параметров для команды Update. Вероятно, пропущен один параметр.");
        }

        if ( !dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ) {
            throw new CantExecuteOrNoConnectionException( "Таблица с таким именем отсутствует." );
        }

        Set<String> columns = new HashSet<>();
        for (int i = 2; i < params.length; i = i + 2) {
            columns.add( params[i]);
        }
        if( !dbManager.existColumns(params[INDEX_OF_TABLE_NAME], DatabaseManager.EXISTENCE_THESE_FIELDS, columns)) {
            throw new CantExecuteOrNoConnectionException( String.format("Набор столбцов указан неверно"));
        }
    }

}
