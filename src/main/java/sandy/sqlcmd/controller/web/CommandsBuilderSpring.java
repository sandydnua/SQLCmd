package sandy.sqlcmd.controller.web;

import lombok.Setter;
import org.springframework.stereotype.Component;
import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

@Component
public class CommandsBuilderSpring implements CommandsBuilder {

    @Setter
    private CommandFactory commandFactoryBean;

    @Override
    public Command createCommand(String[] params, DatabaseManager dbManager) {
        Command command = commandFactoryBean.getCommand(params[0]);
        command.setDbManager(dbManager);
        command.setParams(params);
        return command;
    }

}
