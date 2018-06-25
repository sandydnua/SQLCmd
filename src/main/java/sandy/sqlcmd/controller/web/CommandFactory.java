package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.model.command.Command;

public interface CommandFactory {
    Command getCommand(String name);
}
