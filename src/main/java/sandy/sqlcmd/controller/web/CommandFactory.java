package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.controller.command.Command;

public interface CommandFactory {
    Command getCommand(String name);
}
