package sandy.sqlcmd.controller;

import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.FactoryCommand;
import sandy.sqlcmd.model.JDBCDatabaseManager;
import sandy.sqlcmd.view.View;

import java.util.*;

class Controller {

    private final View view;
    private final DatabaseManager dbManager;
    private boolean continueWork = true;
    private DataSet data;

    public Controller(View view) {
        this.view = view;
        dbManager = new JDBCDatabaseManager();
    }

    public void run() {

        view.write("Для справки введите HELP");
        String inputString;
        while (continueWork) {
            inputString = view.read();
            data = new DataSet();
            try {

                Command command = FactoryCommand.getCommand(prepareParams(inputString));
                command.setDbManager(dbManager);
                data = command.execute();

            } catch (Exception ex) {
                handleException(ex);
            }
            view.write(data);
        }
    }

    private void handleException(Exception ex) {

        if (
                ex instanceof IllegalArgumentException |
                        ex instanceof MainProcessException |
                        ex instanceof IncorrectParametersQuery
                ) {
            data.addString(ex.getMessage());
            return;
        }

        if (ex instanceof CantExecuteException) {
            String[] strings = ex.getMessage().split("\\|");
            data.addString(strings);
            return;
        }

        if (ex instanceof CompletionOfWorkException) {
            data.addString(ex.getMessage());
        } else {
            data.addString("Ума не приложу как это вышло...");
            data.addString("Произошла непредвиденная ошибка");
            try {
                dbManager.disconnect();
            } catch (MainProcessException e) {
                data.addString("Не удалось закрыть текущее подключение");
            }
            data.addString(ex.getMessage());
        }
        continueWork = false;

    }


    // !!!!!! Нужен рефакторинг !!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static String[] prepareParams(String inputStr) {
        List<String> resultList = new ArrayList<>();
        if (null == inputStr) {
            throw new IllegalArgumentException("Нет команды для обработки");
        }
        inputStr = inputStr.trim();
        int inputStrLen = inputStr.length();

        if (inputStrLen == 0) {
            throw new IllegalArgumentException("Зачем Ты ввел пустую строку?");
        }

        int leftPos = 0;
        while (leftPos < inputStrLen) {
            if (inputStr.startsWith(" ", leftPos)) {
                leftPos++;
                continue;
            }
            int tmpL = -1;
            if (inputStr.startsWith("\"", leftPos)) {
                tmpL = leftPos;
            }

            if (tmpL >= 0 && tmpL < inputStrLen) {
                int tmpR = inputStr.indexOf('"', tmpL + 1);
                if (tmpR >= 0) {
                    resultList.add(inputStr.substring(tmpL + 1, tmpR));
                    leftPos = tmpR + 1;
                    continue;
                } else {
                    throw new IllegalArgumentException("Нет закрывающей кавычки!");
                }
            }
            int tmpR = inputStr.indexOf(" ", leftPos);
            if (tmpR >= 0) {
                resultList.add(inputStr.substring(leftPos, tmpR));
                leftPos = tmpR;
            } else {
                if (inputStr.indexOf("\"", leftPos) >= 0) {
                    throw new IllegalArgumentException("Лишняя кавычка!");
                }
                resultList.add(inputStr.substring(leftPos, inputStrLen));
                leftPos = inputStrLen;
            }
        }

        return resultList.toArray(new String[resultList.size()]);
    }
}
