package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.*;
import sandy.sqlcmd.model.databasemanagement.entity.AdministratorRepository;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.services.Services;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private DatabaseManager dbManager;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    @Qualifier(value = "commandFactorySpring")
    CommandsBuilder commandsBuilder;

    @GetMapping("edithelp")
    public String editHelp(HttpSession session) {
        String loginAuthorizedUser = (String) session.getAttribute("administratorLogin");
        return authorizedUserIsAdmin(loginAuthorizedUser) ? "edithelp" : "login";
    }

    private boolean authorizedUserIsAdmin(String loginAuthorizedUser) {
        return administratorRepository.findByLogin(loginAuthorizedUser) !=null ? true : false;
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("logout")
    public String loginout(HttpSession session) {
        session.removeAttribute("administratorLogin");
        return "redirect:help";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpSession session,
                   @RequestParam(value = "login") String login,
                   @RequestParam(value = "password") String password) {
        if( administratorRepository.findByLoginAndPassword(login, password) != null) {
            session.setAttribute("administratorLogin", login);
            return "redirect:edithelp";
        } else {
            session.removeAttribute("administratorLogin");
            request.setAttribute("ErrorLogin", "Неверный логин или пароль ");
            return "login";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpSession session) {
         return isConnect(session)? "index" : "connect";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpSession session) {
         return main(session);
    }

    @GetMapping("connect")
    public String connect() {
        return "connect";
    }

    @GetMapping("help")
    public String help() {
          return "help";
    }

    @PostMapping("connect")
    public String connect(HttpServletRequest request, HttpSession session){
        try {
            executeCommand("connect", request);
            session.setAttribute("dbManager", dbManager);
           return "redirect:/";
        } catch (Exception e) {
            request.setAttribute("ErrorConnect", "Не удалось подключиться. " + e.getMessage() + ". " + e.getClass());
            return "connect";
        }
    }

    @PostMapping("disconnect")
    public String disconnect(HttpServletRequest request, HttpSession session, Model model) {
        try {
            executeCommand("disconnect", request);
            session.removeAttribute("dbManager");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("Error", "Disconnect" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
    }

    private DataSet executeCommand(String action, HttpServletRequest request) throws Exception {

        String[] params = Services.BuilCommandString(action, request);
        Command command = commandsBuilder.getCommand(params);
        command.setDbManager(dbManager);

        return command.execute();
    }

    private boolean isConnect(HttpSession session) {
        DatabaseManager dbm = (DatabaseManager) session.getAttribute("dbManager");
        return dbm != null && dbm.isConnect();
    }
}
