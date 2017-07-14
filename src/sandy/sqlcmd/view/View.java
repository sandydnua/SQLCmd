package sandy.sqlcmd.view;

import sandy.sqlcmd.model.DataSet;

public interface View {
    String read();
    void write(DataSet data);
    void write(String line);

}
