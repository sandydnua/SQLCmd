package sandy.sqlcmd.controller;

import org.apache.log4j.varia.NullAppender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class Main {

    static {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());
    }
    public static void main(String[] args){

        ApplicationContext ap = new AnnotationConfigApplicationContext(AppConsoleContext.class);
        ControllerCmd controllerCmd = (ControllerCmd) ap.getBean("controllerConsole");

        controllerCmd.run();
    }
}
