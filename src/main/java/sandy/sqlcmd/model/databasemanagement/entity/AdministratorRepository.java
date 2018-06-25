package sandy.sqlcmd.model.databasemanagement.entity;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.databasemanagement.entity.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {
    Administrator findByLogin(String login);
    Administrator findByLoginAndPassword(String login, String password);
}
