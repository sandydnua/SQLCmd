package sandy.sqlcmd.model.databasemanagement.mongo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.bson.types.ObjectId;

@Entity
@Table(name = "logItems")
public class LogItem implements Serializable {

    @Id
    private ObjectId id;
    private String message;
    private String timestamp;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogItem [id=" + id + ", message=" + message + ", timestamp=" + timestamp + "]";
    }
}