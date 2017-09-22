package sandy.sqlcmd.controller.web;

public final class HelpInformation {
    private String command;
    private String description;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HelpInformation(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public HelpInformation() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
