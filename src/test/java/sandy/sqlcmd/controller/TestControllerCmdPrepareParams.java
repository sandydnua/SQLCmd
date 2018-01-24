package sandy.sqlcmd.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sandy.sqlcmd.services.Preparer;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@RunWith( Parameterized.class )
public class TestControllerCmdPrepareParams {

    private final String strParams;
    private final String[] paramsExpected;

    public TestControllerCmdPrepareParams(String strParams, String[] params) {

        this.strParams = strParams;
        this.paramsExpected = params;
    }

    @Parameterized.Parameters
    public static List<Object[]> parametersForConstructor() {

        return Arrays.asList( new Object[][]
                {
                        {"exit", new String[]{"exit"} },
                        {"exit help", new String[]{"exit","help"} },
                        {"exit \" \"", new String[]{"exit", " "} },
                        {"exit \"\"", new String[]{"exit", ""} },
                        {"insert tableName title \"Two words\"", new String[]{"insert", "tableName", "title", "Two words"} },
                        {"insert tableName title \"Words\"", new String[]{"insert", "tableName", "title", "Words"} },
                        {"insert tableName title \"Three different words\"", new String[]{"insert", "tableName", "title", "Three different words"} },
                        {"find table", new String[]{"find", "table"} }
                }
        );
    }

    @Test
    public void prepareParams() throws Exception {

        String[] actual = Preparer.split( strParams );
        assertArrayEquals( paramsExpected, actual );
    }

}