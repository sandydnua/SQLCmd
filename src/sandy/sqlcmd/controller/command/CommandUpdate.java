package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;

public class CommandUpdate extends Command {

    public CommandUpdate(String[] params) {
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException, IncorretParametersQuery {
        DataSet data = new DataSet();
        SQLConstructor sqlConstructor = dbManager.getSQLConstructor();
        sqlConstructor.addTables(params[1]);
        sqlConstructor.setColumnAndValueForWhere(params[2], params[3]);
        sqlConstructor.setForColumnNewValue(params[4], params[5]);
        String sqlQuerySelect = sqlConstructor.getQuerySelect();
        String sqlQueryUpdate = sqlConstructor.getQueryUpdate();

        data = dbManager.executeQuery(sqlQuerySelect);
        if (data.quantityRows() > 1) {
            dbManager.executeUpdate(sqlQueryUpdate);
            data.addString("Cтроки, которые будут обновлены");
        } else {
            data = new DataSet("С такими параметрами строки не найдены.");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteException {
        checkConnectAndMinQuantityParameters(6);
    }


}
