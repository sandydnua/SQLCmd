package sandy.sqlcmd.model;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.entity.Language;

public interface LanguagesRepository  extends CrudRepository<Language, Integer>{
    Language findByLanguage(String language);
}
