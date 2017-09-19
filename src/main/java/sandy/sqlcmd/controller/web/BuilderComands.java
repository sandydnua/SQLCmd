package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.controller.command.Command;

public interface BuilderComands {
    Command getCommand(String[] params);
}
