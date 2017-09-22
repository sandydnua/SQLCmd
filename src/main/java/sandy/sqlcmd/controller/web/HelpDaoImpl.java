package sandy.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HelpDaoImpl implements HelpDao {

    @Autowired
    private JdbcTemplate templateHelpDao;

    @Override
    public void insert(String command, String description) {
        templateHelpDao.update("INSERT INTO help (command, description) VALUES (?,?)", command ,description);
    }

    @Override
    public List<HelpInformation> getHelpList() {
        return templateHelpDao.query("SELECT * FROM help", new RowMapper<HelpInformation>() {
            @Override
            public HelpInformation mapRow(ResultSet resultSet, int i) throws SQLException {
                HelpInformation helpRow = new HelpInformation();
                helpRow.setId(resultSet.getInt("id"));
                helpRow.setCommand(resultSet.getString("command"));
                helpRow.setDescription(resultSet.getString("description"));
                return helpRow;
            }
        });
    }

    @Override
    public void delete(int i) {
        templateHelpDao.update("DELETE FROM help WHERE id = ?", new Object[]{i});
    }

    @Override
    public void update(int id, String command, String description) {
       templateHelpDao.update("UPDATE help SET command = ?, description = ? WHERE id = ?", new Object[]{command, description, id});

    }
}
