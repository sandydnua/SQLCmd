package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

public interface CommandsBuilder {
    Command createCommand(String[] params, DatabaseManager dbManager);
}
