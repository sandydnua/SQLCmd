package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandInsert extends Command {
    String sqlQuery = "INSERT INTO <table> ( <columns> ) VALUES ( <values> )";
    public CommandInsert(String[] params) {
        super(params);
    }
    private void prepareSql() {
        StringBuilder  columns = new StringBuilder("");
        StringBuilder  values = new StringBuilder("");
        for( int i = 3 ; i < params.length; i+=2 ){
             columns.append(params[i-1]);
             values.append("'" + params[i] + "'");
             if( i < params.length-1){
                  values.append(",");
                  columns.append(",");
             }
        }
        sqlQuery = sqlQuery.replace("<table>",params[1]);
        sqlQuery = sqlQuery.replace("<columns>",columns.toString());
        sqlQuery = sqlQuery.replace("<values>",values.toString());
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        prepareSql();
        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Операция прошла успешно");
    }

    @Override
    protected void canExecute() throws CanExecuteException {
        String errorMessage = "";
        try{
            checkConnectAndMinQuantityParameters(4);
        }catch (CanExecuteException ex){
            errorMessage +=ex.getMessage();
        }
        if( (params.length % 2) !=0 ){
            errorMessage += "Вы забыли указать или поле, или значение; ";
        }
        if( !"".equals(errorMessage) ){
            throw new CanExecuteException(errorMessage);
        }

    }
}
