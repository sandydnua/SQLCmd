package sandy.sqlcmd.controller;

import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.AllCommands;
import sandy.sqlcmd.controller.web.JDBCDatabaseManager;
import sandy.sqlcmd.view.View;

class Controller {


    private final View view;
    private DatabaseManager dbManager;
    private boolean continueWork = true;
    private DataSet data;

    public Controller(View view) {
        this.view = view;
        dbManager = new JDBCDatabaseManager();
    }

    public void run() {

        view.write("Для справки введите HELP");
        String inputString;

        while (continueWork) {
            data = new DataSet();
            inputString = view.read();
            try {
                Command command = AllCommands.getCommand(Preparer.split(inputString));
                command.setDbManager(dbManager);
                data = command.execute();
            } catch (Exception ex) {
                continueWork = handleException(ex);
            }
            view.write(data);
        }
    }

    private boolean handleException(Exception ex) {

        if (  ex instanceof IllegalArgumentException |
              ex instanceof MainProcessException |
              ex instanceof IncorrectParametersQuery
           ) {
            data.addString(ex.getMessage());
            return true;
        }

        if ( ex instanceof CantExecuteException ) {
             String[] strings = ex.getMessage().split("\\|");
             data.addString(strings);
             return true;
        }

        if (ex instanceof CompletionOfWorkException) {
            data.addString(ex.getMessage());
        } else {
            data.addString("Ума не приложу как это вышло...");
            data.addString("Произошла непредвиденная ошибка");
            try {
                if(dbManager.isConnect()){
                    dbManager.disconnect();
                }
            } catch (MainProcessException e) {
                data.addString("Не удалось закрыть текущее подключение");
            }
            data.addString(ex.getMessage());
        }
        return  false;
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
}

