package sandy.sqlcmd.controller.web;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.databasemanagement.mongo.LogItem;
import sandy.sqlcmd.model.databasemanagement.mongo.LogService;
import sandy.sqlcmd.services.Services;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class MainRestController {
    @Autowired
    LogService logService;

    @Autowired
    @Qualifier(value = "commandFactorySpring")
    CommandsBuilder commandsBuilder;

    @GetMapping("find")
    public String[][] find(HttpServletRequest request, HttpSession session) {
        return executeCommand("find", request, session);
    }

    @GetMapping("mongotest")
    public void mongotest() {
        LogItem item = new LogItem();
        item.setId(new ObjectId());
        item.setMessage("msg");
        Date d = new Date();
        item.setTimestamp(Long.toString(d.getTime()));
        logService.add(item);
    }

    @RequestMapping(value = "tables", method = RequestMethod.GET)
    public String[][] tables(HttpServletRequest request, HttpSession session) {
       return executeCommand("tables", request, session);
    }

    @PostMapping("dropTable")
    public String[][]  dropTable(HttpServletRequest request, HttpSession session) {
        return executeCommand("drop", request, session);
    }
    @PostMapping("delete")
    public String[][]  delete(HttpServletRequest request, HttpSession session) {
        return executeCommand("delete", request, session);
    }
    @PostMapping("update")
    public String[][]  update(HttpServletRequest request, HttpSession session) {
        return executeCommand("update", request, session);
    }

    @PostMapping("createTable")
    public String[][] createTable(HttpServletRequest request, HttpSession session) {
        return  executeCommand("create", request, session);
    }
    @PostMapping("insert")
    public String[][] insert(HttpServletRequest request, HttpSession session) {
        return executeCommand("insert", request, session);
    }
    @PostMapping("clear")
    public String[][] clear(HttpServletRequest request, HttpSession session) {
        return executeCommand("clear", request, session);
    }
    private String[][] executeCommand(String action, HttpServletRequest request, HttpSession session) {

        Command command = commandsBuilder.getCommand(Services.BuilCommandString(action, request));
        command.setDbManager((DatabaseManager) session.getAttribute("dbManager"));

        try {
            return Services.toTable(command.execute());
        } catch (Exception e) {
            System.out.println("ERROR!!! " + e.getMessage() + e.getClass());
            return null;
        }
    }
}
