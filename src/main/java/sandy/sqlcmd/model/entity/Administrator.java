package sandy.sqlcmd.model.entity;

import javax.persistence.*;

@Entity
@Table( name = "administrators", schema = "public")
public class Administrator {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public Administrator(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Administrator() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
