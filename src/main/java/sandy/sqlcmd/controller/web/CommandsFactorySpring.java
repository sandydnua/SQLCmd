package sandy.sqlcmd.controller.web;

import org.springframework.stereotype.Component;
import sandy.sqlcmd.controller.command.Command;

@Component
public class CommandsFactorySpring implements BuilderComands {

    private CommandFactory commandFactoryBean;

    @Override
    public Command getCommand(String[] params) {
        Command command = commandFactoryBean.getCommand(params[0]);
        command.setParams(params);
        return command;
    }
    public void setCommandFactoryBean(CommandFactory commandFactory) {
        this.commandFactoryBean = commandFactory;
    }
}
