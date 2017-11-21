package sandy.sqlcmd.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "help" , schema = "public")
public final class HelpInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "command_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private  Commands command;

    @Column(name = "description")
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HelpInformation(Commands command, String description) {
        this.command = command;
        this.description = description;
    }

    public HelpInformation() {
    }


    public Commands getCommand() {
        return command;
    }

    public void setCommand(Commands command) {
        this.command = command;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
