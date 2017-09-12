package sandy.sqlcmd.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sandy.sqlcmd.view.View;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.controller.Controller;


class Main {

    public static void main(String[] args){
        View view = new Console();

        Controller controller = new Controller(view);
        controller.run();
    }
}
