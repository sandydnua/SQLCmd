package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;
import sandy.sqlcmd.model.SQLConstructorPostgre;

public class CommandDelete extends Command {
    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int INDEX_OF_COLUMN_FOR_WHERE = 2;
    private static final int INDEX_OF_VALUE_FOR_WHERE = 3;
    private static final int MIN_QUANTITY_PARMETERS = 4;

    public CommandDelete(String[] params) {
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        SQLConstructor sqlConstructor = new SQLConstructorPostgre();
        sqlConstructor.addTables( params[INDEX_OF_TABLE_NAME]);
        sqlConstructor.setColumnAndValueForWhere( params[INDEX_OF_COLUMN_FOR_WHERE], params[INDEX_OF_VALUE_FOR_WHERE] );

        if(params.length > MIN_QUANTITY_PARMETERS) {
            for (int i = 0; i < (params.length-MIN_QUANTITY_PARMETERS) / 2; i++) {
                String field = params[MIN_QUANTITY_PARMETERS + i*2];
                String value = params[MIN_QUANTITY_PARMETERS + 1 + i*2];
                sqlConstructor.addColumnAndValueForWhere(field, value);
            }
        }

        String sqlQuerySelect = sqlConstructor.getQuerySelect();
        String sqlQuery = sqlConstructor.getQueryDelete();

        DataSet data = dbManager.executeQuery(sqlQuerySelect);

        if(data.quantityRows() > 1){
            dbManager.executeUpdate(sqlQuery);
            data.addString("Удалены следующие строки");
        }else{
            data = new DataSet();
            data.addString("Нечего удалять");
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {

        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARMETERS);

        if(params.length > MIN_QUANTITY_PARMETERS && params.length%2 != 0) {
            throw new CantExecuteException("Неверное число параметров.");
        }
    }
}
