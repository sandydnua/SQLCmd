package sandy.sqlcmd.controller;

import sandy.sqlcmd.view.View;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.controller.Controller;


public class Main {

    public static void main(String[] args){
        View view = new Console();
        Controller controller = new Controller(view);
        controller.run();
    }
}