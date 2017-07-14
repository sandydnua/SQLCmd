package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandUpdate extends Command {
    String sqlQueryUpdate = "UPDATE <table> SET <column2> = <value2> WHERE <column1> = <value1>";
    String sqlQuerySelect = "SELECT * FROM <table> WHERE <column1>=<value1>";

    public CommandUpdate(String[] params) {
        super(params);
    }
    private void prepareSql() {
        sqlQueryUpdate = sqlQueryUpdate.replaceFirst("<table>", params[1]);
        sqlQueryUpdate = sqlQueryUpdate.replaceFirst("<column1>", params[2]);
        sqlQueryUpdate = sqlQueryUpdate.replaceFirst("<column2>", params[4]);
        sqlQueryUpdate = sqlQueryUpdate.replaceFirst("<value1>", params[3]);
        sqlQueryUpdate = sqlQueryUpdate.replaceFirst("<value2>", params[5]);

        sqlQuerySelect = sqlQuerySelect.replaceFirst("<table>", params[1]);
        sqlQuerySelect = sqlQuerySelect.replaceFirst("<column1>", params[2]);
        sqlQuerySelect = sqlQuerySelect.replaceFirst("<value1>", params[3]);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        prepareSql();
        DataSet data = new DataSet();
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
    protected void canExecute() throws CanExecuteException {
        checkConnectAndMinQuantityParameters(6);
    }


}
