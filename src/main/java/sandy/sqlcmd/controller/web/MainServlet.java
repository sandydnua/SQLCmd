package sandy.sqlcmd.controller.web;

import sandy.sqlcmd.controller.command.*;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.FactoryCommand;
import sandy.sqlcmd.model.JDBCDatabaseManager;
import sandy.sqlcmd.services.Services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    Map<String, String> supportedCommands;


    @Override
    public void init() {




        supportedCommands = new HashMap<>();
        supportedCommands.put("tables","tables.jsp");
        supportedCommands.put("find","find.jsp");
        supportedCommands.put("create","tables");
        supportedCommands.put("drop","tables");
        supportedCommands.put("insert","find");
        supportedCommands.put("delete","find");
        supportedCommands.put("disconnect","/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        DatabaseManager dbManager = (DatabaseManager) req.getSession(true).getAttribute("dbManager");
        if (dbManager == null || !dbManager.isConnect()) {
            req.getSession(true).removeAttribute("database");
        }


        if(action.equals("/") || action.equals("mainpage")) {
            req.getRequestDispatcher("mainpage.jsp").forward(req, resp);
            return;
        }  else if (supportedCommands.containsKey(action)) {
            String[] params = Services.BuilCommandString(action, req);
            Command command = FactoryCommand.getCommand(params);
            command.setDbManager(dbManager);
            try {
                DataSet data = command.execute();
                if (dbManager.isConnect()){
                    int i = 0;
                }
                req.setAttribute("table",Services.getTable(data));
                req.getRequestDispatcher(supportedCommands.get(action)).forward(req, resp);
            } catch (Exception e) {
                req.setAttribute("Error", String.format("%s<p>%s", e.getClass(),e.getMessage()));
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
            return;

        } else if(action.equals("connect") || action.equals("help") || action.equals("createtable")) {
            req.getRequestDispatcher(action+".jsp").forward(req, resp);
            return;
        } else {
            req.getRequestDispatcher("unknown.jsp").forward(req, resp);
            return;
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String action = requestURI.substring(req.getContextPath().length(), requestURI.length());
        if (action.equals("/") || action.charAt(0) != '/') {
            return action;
        } else {
            return action.substring(1);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if(action.equals("connect")) {

            String adders = req.getParameter("addres");
            String port = req.getParameter("port");
            String dbName = req.getParameter("dbName");
            String login = req.getParameter("login");
            String pass = req.getParameter("pass");
            String[] params = new String[]{"connect", adders + ":" + port, dbName, login, pass};

            CommandConnect connect = new CommandConnect(params);
            try {
                DatabaseManager dbManager = new JDBCDatabaseManager();
                connect.setDbManager(dbManager);
                connect.execute();

                req.getSession().setAttribute("dbManager", dbManager);
                req.getSession(true).setAttribute("database", dbName);

                req.getRequestDispatcher("mainpage.jsp").forward(req, resp);
                return;
            } catch (Exception e) {
                //TODO тут надо и закрывать подключение, если оно было
                req.getSession(true).removeAttribute("dbManager");
                req.getSession(true).removeAttribute("database");

                req.setAttribute("ErrorConnect", "Не удалось подключиться. " + e.getMessage() + ". " + e.getClass());
                req.getRequestDispatcher("connect.jsp").forward(req, resp);
                return;
            }
        }
    }
}
