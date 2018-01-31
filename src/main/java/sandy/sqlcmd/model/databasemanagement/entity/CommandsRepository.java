package sandy.sqlcmd.model.databasemanagement.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommandsRepository extends CrudRepository<Commands, Integer> {

    List<Commands> findAllBy();
    Commands findCommandsByCommandName(String commandName);
    Commands findById(int id);

    @Override
    void delete(Integer integer);

}

