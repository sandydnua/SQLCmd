package sandy.sqlcmd.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@RunWith( Parameterized.class )
public class TestControllerPrepareParams {

    private String strParams;
    private String[] paramsExpected;

    public TestControllerPrepareParams ( String strParams, String[] params) {

        this.strParams = strParams;
        this.paramsExpected = params;
    }

    @Parameterized.Parameters
    public static List<Object[]> parametersForConctructor() {

        return Arrays.asList( new Object[][]
                {
                        { new String("exit" ), new String[]{"exit"} },
                        { new String("exit help" ), new String[]{"exit","help"} },
                        { new String("exit \" \"" ), new String[]{"exit", " "} },
                        { new String("exit \"\"" ), new String[]{"exit", ""} },
                        { new String("insert tableName title \"Two words\"" ), new String[]{"insert", "tableName", "title", "Two words"} },
                        { new String("insert tableName title \"Words\"" ), new String[]{"insert", "tableName", "title", "Words"} },
                        { new String("insert tableName title \"Three different words\"" ), new String[]{"insert", "tableName", "title", "Three different words"} },
                        { new String("find table" ), new String[]{"find", "table"} }
                }
        );
    }

    @Test
    public void prepareParams() throws Exception {

        assertArrayEquals( paramsExpected, Controller.prepareParams(strParams));
    }

}