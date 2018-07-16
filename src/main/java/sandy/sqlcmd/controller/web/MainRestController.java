package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Qualifier(value = "mongoDatabaseManager")
    private DatabaseManager dbManager;

    @Autowired
    @Qualifier(value = "commandFactorySpring")
    CommandsBuilder commandsBuilder;
    @GetMapping({"find","tables"})
    public ResponseEntity<String[][]> getRequest(HttpServletRequest request, HttpSession session) {
        return formResponse(request, session, HttpStatus.NOT_FOUND);
    }
    @PostMapping({"insert","clear","create","update","delete","drop"})
    public ResponseEntity<String[][]> postRequest(HttpServletRequest request, HttpSession session) {
        return formResponse(request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping({"connect"})
    public ResponseEntity<String[][]> connect(HttpServletRequest request, HttpSession session) {
        session.setAttribute("dbManager", dbManager);
        return formResponse(request, session, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("disconnect")
    public ResponseEntity<String[][]> disconnect(HttpServletRequest request, HttpSession session) {
        ResponseEntity<String[][]> response = formResponse(request, session, HttpStatus.INTERNAL_SERVER_ERROR);
        session.removeAttribute("dbManager");
        return response;
    }

    private String[][] executeCommand(HttpServletRequest request, HttpSession session) throws ErrorExecutionOfCommandException {

        Command command = commandsBuilder.createCommand(Services.buildStringOfCommand(request),
                                                        (DatabaseManager) session.getAttribute("dbManager"));
        try {
            return Services.toTable(command.execute());
        } catch (Exception e) {
            throw new ErrorExecutionOfCommandException(e.getMessage());
        }
    }
    private ResponseEntity<String[][]> formResponse(HttpServletRequest request, HttpSession session, HttpStatus statusError) {
        try {
            return new ResponseEntity<>(executeCommand(request, session),
                                        HttpStatus.OK);
        } catch (ErrorExecutionOfCommandException e) {
            String[][] msg = {{e.getMessage()}};
            return new ResponseEntity<>(msg,statusError);
        }
    }
}
