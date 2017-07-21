package sandy.sqlcmd.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith( Parameterized.class )
public class TestControllerPrerareParamsWithIllegalArgument {

    private String strParams;
    private String[] paramsExpected;

    public TestControllerPrerareParamsWithIllegalArgument ( String strParams, String[] params) {
        this.strParams = strParams;
        this.paramsExpected = params;
    }

    @Parameterized.Parameters
    public static List<Object[]> parametersForConctructor() {

        return Arrays.asList( new Object[][]
                {
                        { new String("\"" ), new String[]{""} },
                        { new String("insert tableName title \"Two \" words\"" ), new String[]{""} },
                        { new String("" ), new String[]{""} },
                        { new String("\"help" ), new String[]{""} },
                        { new String("command \" " ), new String[]{""} },
                        { new String("command \" \"\" " ), new String[]{""} },
                        { new String(" " ), new String[]{""} }
                }
        );
    }

    @Test ( expected = IllegalArgumentException.class )
    public void prepareParams() throws Exception {

        Controller.prepareParams(strParams);
    }

}
