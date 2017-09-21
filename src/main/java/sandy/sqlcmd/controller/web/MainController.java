package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.services.Services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private DatabaseManager dbManager;

    @GetMapping("/help")
    public String help() {
        return "help";
    }


    // TODO потом убрать это
    @GetMapping("/test")
    public String test(Model model) {
        List<String> list = new ArrayList();
        for (int i = 1; i < 30; i++) {
            try {
                DataSet dataSet = dbManager.executeQuery("SELECT * FROM tab");
                list.add(Integer.toString(i) + " Yes. " + dataSet.quantityRows());
            } catch (MainProcessException e) {
                list.add(Integer.toString(i) + " No. " + e.getMessage());
            }
        }
        model.addAttribute("list", list.toArray(new String[0]) );
        return "test";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String min() {
        return "index";
    }

    @GetMapping("/connect")
    public String connect() {
        return "connect";
    }

    @PostMapping("/connect")
    public String connect(HttpServletRequest request, HttpSession session,
                          @RequestParam(value = "dbName", required = true) String dbName){

        try {
            executeCommand("connect", request);
            session.setAttribute("database", dbName);
            return "index";
        } catch (Exception e) {
            session.removeAttribute("database");
            request.setAttribute("ErrorConnect", "Не удалось подключиться. " + e.getMessage() + ". " + e.getClass());
            return "connect";
        }

    }

    @PostMapping("/disconnect")
    public String disconnect(HttpServletRequest request, HttpSession session, Model model) {

        try {
            executeCommand("disconnect", request);
        } catch (Exception e) {
            model.addAttribute("Error", "Disconnect" + e.getMessage() + " " + e.getClass().getName());
            return "error";
        }

        session.removeAttribute("database");
        return "redirect:/";
    }

    @GetMapping("createtable")
    public String creaTetableForm(HttpServletRequest request, Model model) {
        String[] fields = request.getParameterValues("fields");
        model.addAttribute("fields",fields);
        return "createtable";
    }

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

    @GetMapping("tables")
    public String tables(HttpServletRequest request, Model model) {
        DataSet data;
        try {
            data = executeCommand("tables", request);
            request.setAttribute("table", Services.getTable(data));
        } catch (Exception e) {
            model.addAttribute("Error", "Tables" + e.getMessage() + " " + e.getClass().getName() );
            return "error";
        }
        return "tables";
    }
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

    @GetMapping("find")
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
    }

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
    }

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

        ApplicationContext context = new ClassPathXmlApplicationContext("context-factory.xml");
        BuilderComands builderComands = (BuilderComands) context.getBean("commandFactorySpring");

        String[] params = Services.BuilCommandString(action, request);
        Command command = builderComands.getCommand(params);
        command.setDbManager(dbManager);

        return command.execute();
    }


}
