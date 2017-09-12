package sandy.sqlcmd.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sandy.sqlcmd.view.View;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.controller.Controller;


class Main {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});

        Controller controller = context.getBean("controllerCmd", Controller.class);
        controller.run();
    }
}
