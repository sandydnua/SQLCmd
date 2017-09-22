package sandy.sqlcmd.controller.web;

import java.util.List;

public interface HelpDao {
    void insert(String command, String description);
    List<HelpInformation> getHelpList();

    void delete(int i);

    void update(int id, String command, String description);
}

