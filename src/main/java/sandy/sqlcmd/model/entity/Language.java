package sandy.sqlcmd.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "languages", schema = "public")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "language")
    private String language;

    @Column(name = "short_name")
    private String shortName;

    public Language() {
    }

    public Language(String language, String shortName) {
        this.language = language;
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
