package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandDelete extends Command {
    // TODO
    String sqlQuery = "DELETE FROM <table> WHERE <colunm> = <value>";
    String sqlQuerySelect = "SELECT * FROM <table> WHERE <colunm> = <value>";
    public CommandDelete(String[] params) {
        super(params);
    }
    private String prepareSql(String query) {
        query = query.replaceFirst("<table>",params[1]);
        query = query.replaceFirst("<colunm>",params[2]);
        query = query.replaceFirst("<value>",params[3]);
        return query;
    }


    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        sqlQuerySelect = prepareSql(sqlQuerySelect);
        sqlQuery = prepareSql(sqlQuery);

        DataSet data = new DataSet();
        data = dbManager.executeQuery(sqlQuerySelect);
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
        checkConnectAndParameters(4);
    }
}
