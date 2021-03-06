package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sandy.sqlcmd.model.databasemanagement.entity.*;

import java.util.Comparator;
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
    public void updateHelpTranslation(@RequestParam( value = "id") int id,
                                      @RequestParam(value = "description") String description) {
        HelpTranslation translation = helpTranslationRepository.findOne(id);
        translation.setDescription(description);
        helpTranslationRepository.save(translation);
    }

    @GetMapping("getHelpTranslations")
    public List<HelpTranslation> getHelpTranslations(@RequestParam(value = "language") int languageId) {
        List<HelpTranslation> list = helpTranslationRepository.findAllByLanguageId(languageId);
        list.sort(Comparator.comparingInt(HelpTranslation::getId));
        return list;
    }

    @GetMapping("languages")
    public List<Language> languages() {
        return (List<Language>) languagesRepository.findAll();
    }

    @PostMapping("deleteLanguage")
    @Transactional
    public void deleteLanguage(@RequestParam(value = "id") int id) {
        helpTranslationRepository.delete(helpTranslationRepository.findAllByLanguageId(id));
        languagesRepository.delete(id);
    }

    @PostMapping("insertLanguage")
    public void insertLanguage(@RequestParam(value = "language") String languageValue,
                               @RequestParam(value = "shortName") String shortName) {
        Language language = new Language(languageValue, shortName);
        languagesRepository.save(language);
        int newId = languagesRepository.findByLanguage(languageValue).getId();

        List<Commands> commands = (List<Commands>) commandsRepository.findAll();
        for (Commands command : commands) {
            HelpTranslation helpTranslation = new HelpTranslation(command, NULL, newId);
            helpTranslationRepository.save(helpTranslation);
        }
    }

    @PostMapping("insertCommand")
    public void insertCommand(@RequestParam(value = "command") String commandvalue,
                              @RequestParam(value = "format") String format) {
        Commands command = new Commands(commandvalue, format);
        commandsRepository.save(command);

        List<Language> languages = (List<Language>) languagesRepository.findAll();
        for (Language language : languages) {
            HelpTranslation helpTranslation = new HelpTranslation(command, NULL, language.getId());
            helpTranslationRepository.save(helpTranslation);
        }
    }

    @PostMapping("deleteCommand")
    @Transactional
    public void deleteCommand(@RequestParam(value = "id") int idForDelete) {
        Commands commandForDelete = commandsRepository.findById(idForDelete);
        helpTranslationRepository.delete(helpTranslationRepository.findAllByCommand(commandForDelete));
        commandsRepository.delete(idForDelete);
    }

    @GetMapping("commands")
    public List<Commands> commands() {
        return (List<Commands>) commandsRepository.findAll();
    }

}
