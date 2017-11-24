package sandy.sqlcmd.model;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.entity.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {
    Administrator findByLogin(String login);
    Administrator findByLoginAndPassword(String login, String password);
}
