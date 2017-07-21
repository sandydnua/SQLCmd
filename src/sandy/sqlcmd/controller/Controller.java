package sandy.sqlcmd.controller;

import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.FactoryCommand;
import sandy.sqlcmd.model.JDBCDatabaseManager;
import sandy.sqlcmd.view.View;

import java.util.*;

public class Controller {
    View view = null;
    Command command = null;
    DatabaseManager dbManager;

    public Controller(View view) {
        this.view = view;
        dbManager = new JDBCDatabaseManager();
    }

    public void run() {

        view.write("Для справки введите HELP");
        try {
            while ( true ) {
                String str = view.read();
                command = FactoryCommand.getCommand(prepareParams(str));
                command.setDbManager(dbManager);
                DataSet data = new DataSet();
                try {
                    data = command.execute();
                } catch ( CantExecuteException ex) {
                    String[] strings = ex.getMessage().split("\\|");
                    data.addString(strings);
                } catch ( MainProcessException ex ){
                    String message = ex.getMessage();
                    data.addString(message);
                } catch ( IncorretParametersQuery ex) {
                    String message = ex.getMessage();
                    data.addString(message);
                }
                view.write(data);
            }
        } catch ( CompletionOfWorkException ex ) {
            view.write(ex.getMessage());
        } catch ( Exception ex ) {

            view.write( "Ума не приложу как это вышло..." );
            view.write( ex.getMessage() );
            ex.printStackTrace();
        }
        return;
    }


    // !!!!!! Нужен рефакторинг !!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static String[] prepareParams(String inputStr) throws IllegalAccessException {
        List<String> resultList = new ArrayList<>();
        if ( null == inputStr) {
            throw new IllegalArgumentException( "Нет команды для обработки" );
        }
        inputStr = inputStr.trim();
        int inputStrLen = inputStr.length();

        if ( inputStrLen == 0) {
            throw new IllegalArgumentException( "Зачем Ты ввел одни пробелы?" );
        }

        int leftPos = 0;
        while ( leftPos < inputStrLen) {
            if ( inputStr.startsWith(" ", leftPos)) {
                leftPos++;
                continue;
            }
            int tmpL = -1;
            if ( inputStr.startsWith("\"", leftPos)) {
                tmpL = leftPos;
            }

            if( tmpL >=0 && tmpL < inputStrLen) {
                int tmpR = inputStr.indexOf('"', tmpL+1);
                if( tmpR >=0 ) {
                    resultList.add(inputStr.substring(tmpL+1,tmpR));
                    leftPos = tmpR+1;
                    continue;
                } else {
                    throw new IllegalArgumentException( "Нет закрывающей кавычки!" );
                }
            }
            int tmpR = inputStr.indexOf(" ", leftPos);
            if( tmpR >= 0 ) {
                resultList.add(inputStr.substring(leftPos, tmpR));
                leftPos = tmpR;
            } else {
                if( inputStr.indexOf("\"", leftPos) >= 0) {
                    throw new IllegalArgumentException( "Лишняя кавычка!" );
                }
                resultList.add(inputStr.substring(leftPos, inputStrLen));
                leftPos = inputStrLen;
            }


        }

        return resultList.toArray( new String[resultList.size()]);
    }
}
