package sandy.sqlcmd.model;

public class FactoryCommand {
    public static Command getCommand(String[] params){
        switch (params[0]){
            case "HELP":
                return new CommandHelp();
            case "EXIT":
                return new CommandExit();
            case "CONNECT":
                return new CommandConnect(params);
            case "DISCONNECT":
                return new CommandDisonnect(params);
            case "TABLES":
                return new CommandTables(params);
            case "FIND":
                return new CommandFind(params);
            case "DROP":
                return new CommandDrop(params);
            case "CLEAR":
                return new CommandClear(params);
            case "DELETE":
                return new CommandDelete(params);
            case "CREATE":
                return new CommandCreate(params);
            case "UPDATE":
                return new CommandUpdate(params);
            case "INSERT":
                return new CommandInsert(params);
            default: return new UnknownCommnad();
        }
    }
}
