package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sandy.sqlcmd.model.databasemanagement.entity.AdministratorRepository;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

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
                   @RequestParam(name = "login") String login,
                   @RequestParam(name = "password") String password) {
        if( administratorRepository.findByLoginAndPassword(login, password) != null) {
            session.setAttribute("administratorLogin", login);
            return "redirect:edithelp";
        } else {
            session.removeAttribute("administratorLogin");
            request.setAttribute("ErrorLogin", "Incorrect login or password");
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

    private boolean isConnect(HttpSession session) {
        DatabaseManager dbm = (DatabaseManager) session.getAttribute("dbManager");
        return dbm != null && dbm.isConnect();
    }
}
