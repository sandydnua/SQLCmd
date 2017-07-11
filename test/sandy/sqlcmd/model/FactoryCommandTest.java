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
                                    { new String[]{"Exit"},new CommandExit()},
                                    { new String[]{"Exit","qwqwq"},new CommandExit()},
                                    { new String[]{"EXIT","qwqwq"},new CommandExit()},
                                    { new String[]{"exit","qwqwq"},new CommandExit()},
                                    { new String[]{"clear","qwqwq"},new CommandClear()},
                                    { new String[]{"connect"},new CommandConnect()},
                                    { new String[]{"create"},new CommandCreate()},
                                    { new String[]{"delete"},new CommandDelete()},
                                    { new String[]{"disconnect"},new CommandDisonnect()},
                                    { new String[]{"Drop"},new CommandDrop()},
                                    { new String[]{"help"},new CommandHelp()},
                                    { new String[]{"find"},new CommandFind()},
                                    { new String[]{"insert"},new CommandInsert()},
                                    { new String[]{"tables"},new CommandTables()},
                                    { new String[]{"tables"},new CommandTables()},
                                    { new String[]{"update"},new CommandUpdate()},
                                    { new String[]{"???"},new UnknownCommnad()},
                                    { new String[]{},new UnknownCommnad()},
                                    { new String[]{""},new UnknownCommnad()},
                                    { new String[]{null},new UnknownCommnad()}
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
