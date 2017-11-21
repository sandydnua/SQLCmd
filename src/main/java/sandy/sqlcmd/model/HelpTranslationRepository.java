package sandy.sqlcmd.model;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.entity.Commands;
import sandy.sqlcmd.model.entity.HelpTranslation;

import java.util.List;

public interface HelpTranslationRepository extends CrudRepository<HelpTranslation, Integer>{
    List<HelpTranslation> findAllByLanguageId(int languageId);
    List<HelpTranslation> findAllByCommand(Commands command);


}
