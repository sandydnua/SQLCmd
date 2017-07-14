package sandy.sqlcmd.controller;

import sandy.sqlcmd.model.Command;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.FactoryCommand;
import sandy.sqlcmd.model.JDBCDatabaseManager;
import sandy.sqlcmd.view.View;

public class Controller {
    View view = null;
    Command command = null;
    DatabaseManager dbManager;

    public Controller(View view) {
        this.view = view;
        dbManager = new JDBCDatabaseManager();
    }

    public void run() {

        view.write("Для справки введите HELP");
        try {
            for (; ; ) {
                String str = view.read();
                command = FactoryCommand.getCommand(prepareParams(str));
                command.setDbManager(dbManager);
                view.write(command.execute());
            }
        }catch(CompletionOfWorkException ex){
            view.write(ex.getMessage());
        }

    }

    private String[] prepareParams(String str) {
        String[] params = str.split("\\|");
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].trim();
        }
        return params;
    }
}
