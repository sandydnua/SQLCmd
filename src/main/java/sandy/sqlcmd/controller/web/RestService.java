package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.model.CommandsRepository;
import sandy.sqlcmd.model.HelpTranslationRepository;
import sandy.sqlcmd.model.LanguagesRepository;
import sandy.sqlcmd.model.entity.Commands;
import sandy.sqlcmd.model.entity.HelpTranslation;
import sandy.sqlcmd.model.entity.Language;
import sandy.sqlcmd.services.Services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import static org.hibernate.sql.InFragment.NULL;

@RestController
public class RestService {
    @Autowired
    @Qualifier(value = "commandFactorySpring")
    BuilderCommands builderCommands;

    @Autowired
    private CommandsRepository commandsRepository;

    @Autowired
    private HelpTranslationRepository helpTranslationRepository;
    @Autowired
    private LanguagesRepository languagesRepository;

    @PostMapping("updateHelpTranslation")
    public void updateHelpTranslation(@RequestParam(name = "id") int id,
                                      @RequestParam(name = "description") String description) {
        HelpTranslation translation = helpTranslationRepository.findOne(id);
        translation.setDescription(description);
        helpTranslationRepository.save(translation);
    }

    @GetMapping("getHelpTranslations")
    public List<HelpTranslation> getHelpTranslations(@RequestParam(name = "language") int languageId) {
        List<HelpTranslation> list = helpTranslationRepository.findAllByLanguageId(languageId);
        list.sort((leftParam, rightParam) -> leftParam.getId() - rightParam.getId());
        return list;
    }

    @GetMapping("languages")
    public List<Language> languages() {
        return (List<Language>) languagesRepository.findAll();
    }
    @PostMapping("deleteLanguage")
    public void deleteLanguage(@RequestParam(name = "id") int id) {
// TODO тут напрашивается что-то с транзакциями
         helpTranslationRepository.delete(helpTranslationRepository.findAllByLanguageId(id));
         languagesRepository.delete(id);
    }

    @PostMapping("insertLanguage")
    public void insertLanguage(@RequestParam(name = "language") String languageName,
                               @RequestParam(name = "shortName") String shortName) {
        Language language = new Language(languageName, shortName);
        languagesRepository.save(language);
        int newId = languagesRepository.findByLanguage(languageName).getId();

        List<Commands> commands = (List<Commands>) commandsRepository.findAll();
        for (Commands command : commands) {
            HelpTranslation helpTranslation = new HelpTranslation(command, NULL, newId);
            helpTranslationRepository.save(helpTranslation);
        }
    }

    @PostMapping("insertCommand")
    public void insertCommand(@RequestParam(name = "command") String commandName,
                              @RequestParam(name = "format") String format) {
        Commands command = new Commands(commandName, format);
        commandsRepository.save(command);

        List<Language> languages = (List<Language>) languagesRepository.findAll();
        for (Language language : languages) {
            HelpTranslation helpTranslation = new HelpTranslation(command, NULL, language.getId());
            helpTranslationRepository.save(helpTranslation);
        }
    }

    @PostMapping("deleteCommand")
    public void deleteCommand(@RequestParam(name = "id") int idForDelete) {
        Commands commandForDelete = commandsRepository.findById(idForDelete);
        helpTranslationRepository.delete(helpTranslationRepository.findAllByCommand(commandForDelete));
        commandsRepository.delete(idForDelete);
    }

    @GetMapping("commands")
    public List<Commands> commands() {
        return (List<Commands>) commandsRepository.findAll();
    }

    @GetMapping("find")
    public String[][] find(HttpServletRequest request, HttpSession session) {
        return executeCommand("find", request, session);
    }

    @GetMapping("tables")
    public String[][] Tables(HttpServletRequest request, HttpSession session) {
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

        Command command = builderCommands.getCommand(Services.BuilCommandString(action, request));
        command.setDbManager((DatabaseManager) session.getAttribute("dbManager"));

        try {
            return Services.toTable(command.execute());
        } catch (Exception e) {
            System.out.println("ERROR!!! " + e.getMessage() + e.getClass());
            return null;
        }
    }
}
