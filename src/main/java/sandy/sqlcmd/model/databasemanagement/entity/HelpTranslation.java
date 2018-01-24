package sandy.sqlcmd.model.databasemanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "translations", schema="public")
public class HelpTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn(name = "command_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Commands command;

    @Column(name = "description")
    private String description;

    @Column(name = "language_id")
    private int languageId;

    public HelpTranslation(Commands command, String description, int languageId) {
        this.command = command;
        this.description = description;
        this.languageId = languageId;
    }

    public HelpTranslation() {
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

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
