package sandy.sqlcmd.controller;

import sandy.sqlcmd.controller.web.DatabaseManager;

public interface ControllerConsole {
    void run();

    void setDbManager(DatabaseManager dbManager);
}
