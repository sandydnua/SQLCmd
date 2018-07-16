package sandy.sqlcmd.model.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.util.HashMap;
import java.util.Map;

public class CommandDelete extends Command {
    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int INDEX_OF_COLUMN_FOR_WHERE = 2;
    private static final int INDEX_OF_VALUE_FOR_WHERE = 3;
    private static final int MIN_QUANTITY_PARMETERS = 4;

    public CommandDelete() {  }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        Map<String, String> condition = new HashMap();
        condition.put( params[INDEX_OF_COLUMN_FOR_WHERE], params[INDEX_OF_VALUE_FOR_WHERE] );

        if(params.length > MIN_QUANTITY_PARMETERS) {
            for (int i = 0; i < (params.length-MIN_QUANTITY_PARMETERS) / 2; i++) {
                condition.put(
                            params[MIN_QUANTITY_PARMETERS + i*2],
                            params[MIN_QUANTITY_PARMETERS + 1 + i*2]
                        );
            }
        }

        String tableName =  params[INDEX_OF_TABLE_NAME];
        DataSet data = dbManager.find(tableName, condition);

        if(data.quantityRows() > 1){
            dbManager.delete(tableName, condition);
            data.addString("Удалены следующие строки");
        }else{
            data = new DataSet();
            data.addString("Нечего удалять");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteOrNoConnectionException {

        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARMETERS);

        if(params.length > MIN_QUANTITY_PARMETERS && params.length%2 != 0) {
            throw new CantExecuteOrNoConnectionException("Неверное число параметров.");
        }
        if(!dbManager.existTable(params[INDEX_OF_TABLE_NAME])) {
            throw new CantExecuteOrNoConnectionException("Table not exist");
        }
    }
}
