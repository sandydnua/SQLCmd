package sandy.sqlcmd.model;

public class CommandCreate extends Command {
    String sqlQuery = "CREATE TABLE <table> ( <columns> )";
    public CommandCreate(String[] params) {
        super();
        setParams(params);

    }
    public CommandCreate(){

    }
    private void prepareSql(){
        String  columns = "";
        for( int i = 2 ; i < params.length; i++ ){
            columns += params[i]+" varchar(255)";
            if( i < params.length-1){
                columns += ",";
            }
        }
        sqlQuery = sqlQuery.replace("<table>",params[1]);
        sqlQuery = sqlQuery.replace("<columns>",columns);
    }
    @Override
    protected DataSet mainProcess() throws CommandUpdate.MainProcessExepion {
        prepareSql();
        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Таблица создана");
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        checkConnectAndMinQuantityParameters(2);
    }

}
