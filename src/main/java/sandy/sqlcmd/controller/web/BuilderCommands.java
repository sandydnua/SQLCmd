package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.controller.command.Command;

public interface BuilderCommands {
    Command getCommand(String[] params);
}
