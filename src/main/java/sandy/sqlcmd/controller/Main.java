package sandy.sqlcmd.controller;

import sandy.sqlcmd.view.View;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.controller.Controller;


class Main {

    public static void main(String[] args){
        View view;
        if (args.length ==0 ) { // костыль нужен для тестов. если TRUE, то будет разноцветный вывод
            view = new Console(true);
        } else {
            view = new Console(false);
        }
        Controller controller = new Controller(view);
        controller.run();
    }
}
