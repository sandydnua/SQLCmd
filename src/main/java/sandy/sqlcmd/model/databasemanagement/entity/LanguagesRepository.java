package sandy.sqlcmd.model.databasemanagement.entity;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.databasemanagement.entity.Language;

public interface LanguagesRepository  extends CrudRepository<Language, Integer>{
    Language findByLanguage(String language);
}
