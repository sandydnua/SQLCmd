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
    private static final int EXPECTED_QUANTITY_OF_PARAMETERS = 4;

    public CommandDelete(String[] params) {
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        SQLConstructor sqlConstructor = new SQLConstructorPostgre();
        sqlConstructor.addTables( params[INDEX_OF_TABLE_NAME]);
        sqlConstructor.setColumnAndValueForWhere( params[INDEX_OF_COLUMN_FOR_WHERE], params[INDEX_OF_VALUE_FOR_WHERE] );
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

        checkConnectAndParameters(EXPECTED_QUANTITY_OF_PARAMETERS);
    }
}
