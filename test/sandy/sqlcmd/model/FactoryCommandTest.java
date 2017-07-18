package sandy.sqlcmd.model;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FactoryCommandTest extends TestCase {
    private  String[] params;
    private  Command expCommand;

    public FactoryCommandTest(String[] params, Command expCommand){
        this.params = params;
        this.expCommand = expCommand;
    }



    @Parameterized.Parameters
    public static Collection numbers(){

        return Arrays.asList( new Object[][]
                                {
                                    { new String[]{"Exit"},new CommandExit( new String[]{""} )},
                                    { new String[]{"Exit","qwqwq"},new CommandExit( new String[]{""} )},
                                    { new String[]{"EXIT","qwqwq"},new CommandExit( new String[]{""} )},
                                    { new String[]{"exit","qwqwq"},new CommandExit( new String[]{""} )},
                                    { new String[]{"clear","qwqwq"},new CommandClear( new String[]{""} )},
                                    { new String[]{"connect"},new CommandConnect( new String[]{""} )},
                                    { new String[]{"create"},new CommandCreate( new String[]{""} )},
                                    { new String[]{"delete"},new CommandDelete( new String[]{""} )},
                                    { new String[]{"disconnect"},new CommandDisonnect( new String[]{""} )},
                                    { new String[]{"Drop"},new CommandDrop( new String[]{""} )},
                                    { new String[]{"help"},new CommandHelp( new String[]{""} )},
                                    { new String[]{"find"},new CommandFind( new String[]{""} )},
                                    { new String[]{"insert"},new CommandInsert( new String[]{""} )},
                                    { new String[]{"tables"},new CommandTables( new String[]{""} )},
                                    { new String[]{"tables"},new CommandTables( new String[]{""} )},
                                    { new String[]{"update"},new CommandUpdate( new String[]{""} )},
                                    { new String[]{"???"},new UnknownCommnad( new String[]{""} )},
                                    { new String[]{},new UnknownCommnad( new String[]{""} )},
                                    { new String[]{""},new UnknownCommnad( new String[]{""} )},
                                    { new String[]{null},new UnknownCommnad( new String[]{""} )}
                                }
                            );
    }


    @Test
    public void testGetCommand(){
        params = this.params;
        expCommand = this.expCommand;
        assertTrue( expCommand.getClass() == FactoryCommand.getCommand(params).getClass() );
    }

}
