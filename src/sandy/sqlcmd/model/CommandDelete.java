package sandy.sqlcmd.model;

import sandy.sqlcmd.sandy.sqlcmd.model.MainProcessExepion;

public class CommandDelete extends Command {
    String sqlQuery = "DELETE FROM <table> WHERE <colunm>=<value>";
    String sqlQuerySelect = "SELECT * FROM <table> WHERE <colunm>=<value>";
    public CommandDelete(String[] params) {
        super();
        setParams(params);

    }

    private String prepareSql(String query) {
        query = query.replaceFirst("<table>",params[1]);
        query = query.replaceFirst("<colunm>",params[2]);
        query = query.replaceFirst("<value>",params[3]);
        return query;
    }


    @Override
    protected DataSet mainProcess() throws MainProcessExepion {
        sqlQuerySelect = prepareSql(sqlQuerySelect);
        sqlQuery = prepareSql(sqlQuery);

        DataSet data = new DataSet();
        data = dbManager.executeQuery(sqlQuerySelect);
        if(data.getSizeTable() > 1){
            dbManager.executeUpdate(sqlQuery);
            data.addString("Удалены следующие строки");
        }else{
            data = new DataSet();
            data.addString("Нечего удалять");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndParameters(4);
    }
}
