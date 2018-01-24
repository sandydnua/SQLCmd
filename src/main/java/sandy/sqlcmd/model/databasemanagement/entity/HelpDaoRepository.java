package sandy.sqlcmd.model.databasemanagement.entity;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.databasemanagement.entity.HelpInformation;

import java.util.List;

public interface HelpDaoRepository extends CrudRepository<HelpInformation, Integer> {

    HelpInformation findById(int id);

    List<HelpInformation> findAllBy();

    @Override
    void delete(Integer integer);

}

