package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandUpdate extends Command {

    private static final int INDEX_OF_TABLE_NAME = 1;
    private static final int INDEX_OF_COLUMN_FOR_WHERE = 2;
    private static final int INDEX_OF_VALUE_FOR_WHERE = 3;
    private static final int INDEX_OF_COLUMN_OF_UPDATED_VALUE = 4;
    private static final int INDEX_OF_NEW_VALUE = 5;
    private static final int MIN_QUANTITY_PARAMETERS = 6;

    public CommandUpdate(String[] params) {
        super(params);
    }

    public CommandUpdate() {
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorrectParametersQuery {

        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[INDEX_OF_TABLE_NAME]);
        sqlConstructor.setColumnAndValueForWhere(
                                                   params[INDEX_OF_COLUMN_FOR_WHERE],
                                                   params[INDEX_OF_VALUE_FOR_WHERE]
                                                );
        sqlConstructor.setForColumnNewValue(
                                               params[INDEX_OF_COLUMN_OF_UPDATED_VALUE],
                                               params[INDEX_OF_NEW_VALUE]
                                           );

        String sqlSelect = sqlConstructor.getQuerySelect();
        String sqlUpdate = sqlConstructor.getQueryUpdate();

        if ( !dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ) {

            return new DataSet( "Таблица с таким именем отсутствует." );
        }

        DataSet data;
        String[] columns = new String[]{ params[INDEX_OF_COLUMN_FOR_WHERE], params[INDEX_OF_COLUMN_OF_UPDATED_VALUE] };

        if( dbManager.existColumns(params[INDEX_OF_TABLE_NAME], DatabaseManager.EXISTENCE_THESE_FIELDS, columns)) {

            data = dbManager.executeQuery(sqlSelect);

            if (data.quantityRows() > 1) {

                dbManager.executeUpdate(sqlUpdate);
                data.addString("Эти строки будут обновлены");
            } else {
                data = new DataSet("С такими параметрами строки не найдены.");
            }
        } else {
            data = new DataSet( "Поля с таким именем отсутствует." );
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {

        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);

    }


}
