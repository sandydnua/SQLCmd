package sandy.sqlcmd.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.databasemanagement.JDBCDatabaseManagerSpring;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.view.View;

@Configuration
public class AppConsoleContext {
    @Bean
    public View view() {
        return new Console();
    }
    @Bean
    public ControllerConsole controllerConsole() {
        ControllerConsole controller = new ControllerCmd(view());
        controller.setDbManager(dbManager());
        return controller;
    }

    private DatabaseManager dbManager() {
        return new JDBCDatabaseManagerSpring();
    }
}