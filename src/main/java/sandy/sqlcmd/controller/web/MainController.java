package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.*;
import sandy.sqlcmd.services.Services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private DatabaseManager dbManager;

    @Autowired
    @Qualifier(value = "commandFactorySpring")
    BuilderCommands builderCommands;

    @GetMapping("edithelp")
    public String editHelp() {
        return "edithelp";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpSession session) {
        if( isConnect(session)) {
            return "index";
        } else {
            return "connect";
        }
    }

    @RequestMapping(value = "mainajax", method = RequestMethod.GET)
    public String mainAjax(HttpSession session) {
        if ( isConnect(session)) {
            return "mainajax";
        } else {
            session.setAttribute("referer", "mainajax");
//            session.setAttribute("currentPage", "menuajax");
            return "connect";
        }
    }

    @GetMapping("connect")
    public String connect() {
        return "connect";
    }

    @PostMapping("connect")
    public String connect(HttpServletRequest request, HttpSession session,
                          @RequestParam(value = "dbName") String dbName){

        try {
            executeCommand("connect", request);
//            session.setAttribute("database", dbName);
            session.setAttribute("dbManager", dbManager);
            if(session.getAttribute("referer") != null) {
                return "redirect:" + session.getAttribute("referer");
            } else {
                return "redirect:/";
            }
        } catch (Exception e) {
//            session.removeAttribute("database");
            session.removeAttribute("dbManager");
            request.setAttribute("ErrorConnect", "Не удалось подключиться. " + e.getMessage() + ". " + e.getClass());
            return "connect";
        }
    }

    @PostMapping("disconnect")
    public String disconnect(HttpServletRequest request, HttpSession session, Model model) {

        try {
            executeCommand("disconnect", request);
        } catch (Exception e) {
            model.addAttribute("Error", "Disconnect" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
//        session.removeAttribute("database");
        session.removeAttribute("dbManager");
//        session.removeAttribute("currentPage");
        return "redirect:/";
    }

 /*   @GetMapping("createtable")
    public String creaTetableForm(HttpServletRequest request, Model model, HttpSession session) {
        if ( isConnect(session)) {
            String[] fields = request.getParameterValues("fields");
            model.addAttribute("fields",fields);
            return "createtable";
        } else {
            session.setAttribute("referer", "createtable");
            return "redirect:connect";
        }
    }*/
/*

    @PostMapping("create")
    public String createTable(HttpServletRequest request, Model model) {
        try {
            executeCommand("create", request);
        } catch (Exception e) {
            model.addAttribute("Error", "createTable" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
        return "redirect:tables";
    }
*/
/*
    @GetMapping("tables")
    public String tables(HttpServletRequest request, Model model, HttpSession session) {
        DataSet data;
        try {
            data = executeCommand("tables", request);
            request.setAttribute("table", Services.getTable(data));
        } catch (CantExecuteNoConnectionException e) {
            session.setAttribute("referer", "tables");
            return "redirect:connect";
        }
        catch (Exception e) {
            model.addAttribute("Error", "Tables" + e.getMessage() + " " + e.getClass().getName() );
            return "error";
        }
        return "tables";
    }*/
    @PostMapping("drop")
    public String drop(HttpServletRequest request, Model model) {
        try {
            executeCommand("drop", request);
        } catch (Exception e) {
            model.addAttribute("Error",  "Drop" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
        return "redirect:tables";
    }

   /* @GetMapping("find")
    public String find(HttpServletRequest request, Model model) {
        DataSet data;
        try {
            data = executeCommand("find", request);
            request.setAttribute("table", Services.getTable(data));
        } catch (Exception e) {
            model.addAttribute("Error", "Find" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
        return "find";
    }*/
/*
    @PostMapping("insert")
    public String insert(HttpServletRequest request, Model model) {
        try {
            executeCommand("insert", request);
        } catch (Exception e) {
            model.addAttribute("Error", "Insert" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
        model.addAttribute("table", request.getParameter("table"));
        return "redirect:find";
    }*/

    @PostMapping("delete")
    public String delete(HttpServletRequest request, Model model) {

        try {
            executeCommand("delete", request);
        } catch (Exception e) {
            model.addAttribute("Error", "Delete" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
        model.addAttribute("table", request.getParameter("table"));
        return "redirect:find";
    }

    @PostMapping("update")
    public String update(HttpServletRequest request, Model model) {

        try {
            executeCommand("update", request);
        } catch (Exception e) {
            model.addAttribute("Error", "Update" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }
        model.addAttribute("table", request.getParameter("table"));
        return "redirect:find";
    }

    private DataSet executeCommand(String action, HttpServletRequest request) throws Exception {

        String[] params = Services.BuilCommandString(action, request);
        Command command = builderCommands.getCommand(params);
        command.setDbManager(dbManager);

        return command.execute();
    }

    private boolean isConnect(HttpSession session) {
        DatabaseManager dbm = (DatabaseManager) session.getAttribute("dbManager");
        return dbm != null && dbm.isConnect();
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
