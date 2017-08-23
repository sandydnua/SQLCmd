package sandy.sqlcmd.model;

import sandy.sqlcmd.controller.command.*;

public class FactoryCommand {

    public static Command getCommand(String[] params) {

        if (null == params || params.length <= 0 || null == params[0]) {

            return new UnknownCommand( new String[]{"unknown"});

        }
        switch ( params[0].toUpperCase() ) {
            case "HELP":
                return new CommandHelp( params );
            case "EXIT":
                return new CommandExit( params );
            case "CONNECT":
                return new CommandConnect( params );
            case "DISCONNECT":
                return new CommandDisconnect( params );
            case "TABLES":
                return new CommandTables( params );
            case "FIND":
                return new CommandFind( params );
            case "DROP":
                return new CommandDrop( params );
            case "CLEAR":
                return new CommandClear( params );
            case "DELETE":
                return new CommandDelete( params );
            case "CREATE":
                return new CommandCreate( params );
            case "UPDATE":
                return new CommandUpdate( params );
            case "INSERT":
                return new CommandInsert( params );
            default:
                return new UnknownCommand( params );
        }
    }
}
