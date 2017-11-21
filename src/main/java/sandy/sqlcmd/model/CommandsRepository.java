package sandy.sqlcmd.model;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.entity.Commands;

import java.util.List;

public interface CommandsRepository extends CrudRepository<Commands, Integer> {

    List<Commands> findAllBy();
    Commands findCommandsByCommandName(String commandName);
    Commands findById(int id);

    @Override
    void delete(Integer integer);

}

