package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sandy.sqlcmd.model.Exceptions.ErrorExecutionOfCommandException;
import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.services.Services;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class MainRestController {

    @Autowired
    @Qualifier(value = "commandFactorySpring")
    CommandsBuilder commandsBuilder;

    @GetMapping("find")
    public ResponseEntity<String[][]> find(HttpServletRequest request, HttpSession session) {
        return formResponse("find", request, session, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "tables", method = RequestMethod.GET)
    public ResponseEntity<String[][]> tables(HttpServletRequest request, HttpSession session) {
        return formResponse("tables", request, session, HttpStatus.NOT_FOUND);
    }

    @PostMapping("dropTable")
    public ResponseEntity<String[][]> dropTable(HttpServletRequest request, HttpSession session) {
        return formResponse("drop", request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("delete")
    public ResponseEntity<String[][]> delete(HttpServletRequest request, HttpSession session) {
        return formResponse("delete", request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("update")
    public ResponseEntity<String[][]> update(HttpServletRequest request, HttpSession session) {
        return formResponse("update", request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("createTable")
    public ResponseEntity<String[][]> createTable(HttpServletRequest request, HttpSession session) {
        return formResponse("create", request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("insert")
    public ResponseEntity<String[][]> insert(HttpServletRequest request, HttpSession session) {
        return formResponse("insert", request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("clear")
    public ResponseEntity<String[][]> clear(HttpServletRequest request, HttpSession session) {
        return formResponse("clear", request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private String[][] executeCommand(String action, HttpServletRequest request, HttpSession session) throws ErrorExecutionOfCommandException {

        Command command = commandsBuilder.getCommand(Services.BuildStringOfCommand(action, request));
        command.setDbManager((DatabaseManager) session.getAttribute("dbManager"));

        try {
            return Services.toTable(command.execute());
        } catch (Exception e) {
            String msg = String.format("Error execution of command '%s', Exception: %s: %s",
                                            action,
                                            e.getClass().getName(),
                                            e.getMessage());
            throw new ErrorExecutionOfCommandException(msg);
        }
    }
    private ResponseEntity<String[][]> formResponse(String action, HttpServletRequest request, HttpSession session, HttpStatus statusError) {
        try {
            return new ResponseEntity<String[][]>(
                    executeCommand(action, request, session),
                    HttpStatus.OK);
        } catch (ErrorExecutionOfCommandException e) {
            return new ResponseEntity(statusError);
        }
    }
}
