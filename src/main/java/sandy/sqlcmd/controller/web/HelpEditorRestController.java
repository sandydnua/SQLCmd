package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sandy.sqlcmd.model.databasemanagement.entity.*;
import java.util.List;
import static org.hibernate.sql.InFragment.NULL;

@RestController
public class HelpEditorRestController {

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

}
