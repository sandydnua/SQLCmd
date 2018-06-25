package sandy.sqlcmd.model.databasemanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "commands", schema = "public")
public final class Commands {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "command")
    private String commandName;

    @Column(name = "format")
    private String format;

    public Commands(String commandName, String format) {
        this.commandName = commandName;
        this.format = format;
    }

    public Commands() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
