package sandy.sqlcmd.controller;

import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.AllCommands;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.services.Preparer;
import sandy.sqlcmd.view.View;

class ControllerCmd implements ControllerConsole {
    private final View view;
    private DatabaseManager dbManager;
    private boolean continueWork = true;
    private DataSet data;

    public ControllerCmd(View view) {
        this.view = view;
    }
    @Override
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

        if ( ex instanceof CantExecuteOrNoConnectionException) {
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
    @Override
    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

}

