package sandy.sqlcmd.view;

import sandy.sqlcmd.model.DataSet;

public interface View {
    public String read();
    public void write(DataSet data);
    public void write(String line);

}
