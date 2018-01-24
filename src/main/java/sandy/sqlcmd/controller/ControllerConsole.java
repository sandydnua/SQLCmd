package sandy.sqlcmd.controller;

import sandy.sqlcmd.controller.web.CommandsBuilder;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

public interface ControllerConsole {
    void run();

    void setDbManager(DatabaseManager dbManager);
}
