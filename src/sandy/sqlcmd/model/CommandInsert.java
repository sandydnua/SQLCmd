package sandy.sqlcmd.model;

public class CommandInsert extends Command {
    String sqlQuery = "INSERT INTO <table> ( <columns> ) VALUES ( <values> )";
    public CommandInsert(String[] params) {
        super();
        setParams(params);
    }
    public CommandInsert(){

    }
    private void prepareSql() {
        String  columns = "";
        String  values = "";
        for( int i = 3 ; i < params.length; i+=2 ){
             columns += params[i-1];
             values +="'" + params[i] + "'";
             if( i < params.length-1){
                  values += ",";
                  columns += ",";
             }
        }
        sqlQuery = sqlQuery.replace("<table>",params[1]);
        sqlQuery = sqlQuery.replace("<columns>",columns);
        sqlQuery = sqlQuery.replace("<values>",values);
    }

    @Override
    protected DataSet mainProcess() throws CommandUpdate.MainProcessExepion {
        prepareSql();
        dbManager.executeUpdate(sqlQuery);
        return new DataSet("Операция прошла успешно");
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
        String errorMessage = "";
        try{
            checkConnectAndMinQuantityParameters(4);
        }catch (CanExecuteExeption ex){
            errorMessage +=ex.getMessage();
        }
        if( (params.length % 2) !=0 ){
            errorMessage += "Вы забыли указать или поле, или значение; ";
        }
        if( !"".equals(errorMessage) ){
            throw new CanExecuteExeption(errorMessage);
        }

    }
}
