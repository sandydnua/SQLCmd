package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.model.command.Command;

public interface CommandsBuilder {
    Command getCommand(String[] params);
}
