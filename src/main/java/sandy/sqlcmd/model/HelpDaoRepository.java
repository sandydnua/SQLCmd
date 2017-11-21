package sandy.sqlcmd.model;

import org.springframework.data.repository.CrudRepository;
import sandy.sqlcmd.model.entity.HelpInformation;

import java.util.List;

public interface HelpDaoRepository extends CrudRepository<HelpInformation, Integer> {

    HelpInformation findById(int id);

    List<HelpInformation> findAllBy();

    @Override
    void delete(Integer integer);

}

