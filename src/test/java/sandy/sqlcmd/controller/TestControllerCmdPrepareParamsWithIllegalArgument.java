package sandy.sqlcmd.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sandy.sqlcmd.services.Preparer;

import java.util.Arrays;
import java.util.List;

@RunWith( Parameterized.class )
public class TestControllerCmdPrepareParamsWithIllegalArgument {

    private final String strParams;

    public TestControllerCmdPrepareParamsWithIllegalArgument(String strParams, String[] params) {
        this.strParams = strParams;
    }

    @Parameterized.Parameters
    public static List<Object[]> parametersForConstructor() {

        return Arrays.asList( new Object[][]
                {
                        {"\"", new String[]{""} },
                        {"insert tableName title \"Two \" words\"", new String[]{""} },
                        {"", new String[]{""} },
                        {"\"help", new String[]{""} },
                        {"command \" ", new String[]{""} },
                        {"command \" \"\" ", new String[]{""} },
                        {" ", new String[]{""} }
                }
        );
    }

    @Test ( expected = IllegalArgumentException.class )
    public void prepareParams() throws Exception {
        Preparer.splitToCommands( strParams );
    }

}
