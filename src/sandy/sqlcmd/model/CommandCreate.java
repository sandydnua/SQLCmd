package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandCreate extends Command {
    String sqlQuery = "CREATE TABLE <table> ( <columns> )";
    public CommandCreate(String[] params) {
        super(params);
        //setParams(params);

    }
    private void prepareSql(){
        StringBuilder columns = new StringBuilder("");

        for( int i = 2 ; i < params.length; i++ ){
            columns.append( params[i]+" varchar(255)" );
            if( i < params.length-1){
                columns.append(",");
            }
        }

        sqlQuery = sqlQuery.replace("<table>",params[1]);
        sqlQuery = sqlQuery.replace("<columns>",columns.toString());
    }
    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        prepareSql();
        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Таблица создана");
    }

    @Override
    protected void canExecute() throws CanExecuteException {
        checkConnectAndMinQuantityParameters(2);
    }

}
