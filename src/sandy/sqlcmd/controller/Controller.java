package sandy.sqlcmd.controller;

import sandy.sqlcmd.model.Command;
import sandy.sqlcmd.model.FactoryCommand;
import sandy.sqlcmd.view.View;

public class Controller {
    View view = null;
    Command command = null;
    FactoryCommand.DatabaseManager dbManager;

    public Controller(View view) {
        this.view = view;
        dbManager = new FactoryCommand.JDBCDatabaseManager();
    }

    public void run() {
        view.write("Для справки введите HELP");
        for (; ; ) {
            String str = view.read();
            command = FactoryCommand.getCommand(prepareParams(str));
            command.setDbManager(dbManager);
            view.write(command.execute());
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
