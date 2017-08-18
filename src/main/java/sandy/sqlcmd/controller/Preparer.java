package sandy.sqlcmd.controller;

import java.util.ArrayList;
import java.util.List;

class Preparer {

    static StringBuilder itemCommnad;
    static List<String> resultList;
    static int lengthOfInputString;
    static String inputString;

    static public String[] split(String inputStr) throws IllegalAccessException {

        inputString = inputStr.trim();
        lengthOfInputString = inputString.length();
        resultList = new ArrayList<>();

        if (lengthOfInputString == 0) {
            throw new IllegalArgumentException("Зачем Ты ввел пустую строку?");
        }

        startAnalisisWithSombol(0);

        return resultList.toArray( new String[resultList.size()] );
    }

    private static void startAnalisisWithSombol(int pos) throws IllegalArgumentException {
        itemCommnad = new StringBuilder();
        if ( pos >= lengthOfInputString) {
            return;
        } else if ( inputString.charAt( pos ) == '"') {
            commandInQoutes( pos + 1 );
        } else if ( Character.isDigit( inputString.charAt( pos ) ) || Character.isLetter( inputString.charAt( pos ) ) ) {
            itemCommnad.append( inputString.charAt( pos ) );
            commandWithoutQuotes( pos + 1 );
        } else if (inputString.charAt( pos ) == ' ') {
            startAnalisisWithSombol( pos + 1 );
        } else{
            throw new IllegalArgumentException( "Ошибка в структуре строки" );
        }
    }

    private static void commandInQoutes(int pos) throws IllegalArgumentException {
        if ( pos == lengthOfInputString) {
            throw new IllegalArgumentException( "Нет закрывающей кавычки" );
        } else if ( inputString.charAt( pos ) == '"') {
            resultList.add(itemCommnad.toString());
            startAnalisisWithSombol(pos + 1);
            return;
        }
        itemCommnad.append( inputString.charAt( pos ) );
        commandInQoutes( pos + 1 );
    }

    private static void commandWithoutQuotes(int pos) throws IllegalArgumentException {
        if ( pos == lengthOfInputString || inputString.charAt( pos ) == ' ' ) {
            resultList.add(itemCommnad.toString());
            startAnalisisWithSombol(pos + 1);
            return;
        } else if ( inputString.charAt( pos ) == '"') {
            throw new IllegalArgumentException( "Нет закрывающей кавычки" );
        }
        itemCommnad.append( inputString.charAt( pos ) );
        commandWithoutQuotes( pos + 1 );
    }
}
