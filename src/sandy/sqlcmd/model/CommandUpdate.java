package sandy.sqlcmd.model;

public class CommandUpdate extends Command {
    String sqlQueryUpdate = "UPDATE <table> SET <column2> = <value2> WHERE <column1> = <value1>";
    String sqlQuerySelect = "SELECT * FROM <table> WHERE <column1>=<value1>";

    public CommandUpdate(){

    }
    public CommandUpdate(String[] params) {
        super();
        setParams(params);
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
    protected DataSet mainProcess() throws MainProcessExepion {
        prepareSql();
        DataSet data = new DataSet();
        data = dbManager.executeQuery(sqlQuerySelect);

        if (data.getSizeTable() > 1) {
            dbManager.executeUpdate(sqlQueryUpdate);
            data.addString("Cтроки, которые будут обновлены");
        } else {
            data = new DataSet("С такими параметрами строки не найдены.");
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndMinQuantityParameters(6);
    }

    public static class MainProcessExepion extends Throwable {
        public MainProcessExepion(String message) {
            super(message);
        }
    /*
        public MainProcessExepion(SQLException e) {
            super(e);
        }*/
    /*
        public MainProcessExepion(RuntimeException e) {
            super(e);
        }*/
    }
}
