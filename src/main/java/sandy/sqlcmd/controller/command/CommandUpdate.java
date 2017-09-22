package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        for (int i = MIN_QUANTITY_PARAMETERS; i < params.length; i = i + 4 ) {
            sqlConstructor.addColumnAndValueForWhere(params[i],params[i+1]);
            sqlConstructor.addForColumnNewValue(params[i+2],params[i+3]);
        }

        String sqlSelect = sqlConstructor.getQuerySelect();
        String sqlUpdate = sqlConstructor.getQueryUpdate();

        DataSet data = dbManager.executeQuery(sqlSelect);

        if (data.quantityRows() > 1) {

            dbManager.executeUpdate(sqlUpdate);
            data.addString("Эти строки будут обновлены");
        } else {
            data = new DataSet("С такими параметрами строки не найдены.");
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException, MainProcessException {

        checkConnectAndMinQuantityParameters(MIN_QUANTITY_PARAMETERS);

        if ( (params.length - 2) % 4 != 0) {
            throw new CantExecuteException("Неверное число параметров для команды Update. Вероятно, пропущен один параметр.");
        }

        if ( !dbManager.existTable(params[INDEX_OF_TABLE_NAME]) ) {
            throw new CantExecuteException( "Таблица с таким именем отсутствует." );
        }

        List<String> columns = new ArrayList<>();
        for (int i = 2; i < params.length; i = i + 2) {
            columns.add(params[i]);
            if( dbManager.existColumns(params[INDEX_OF_TABLE_NAME], DatabaseManager.EXISTENCE_THESE_FIELDS, params[i])) {
                throw new CantExecuteException( String.format("Столбец %s не существует", params[i] ));
            }
        }
    }

}
