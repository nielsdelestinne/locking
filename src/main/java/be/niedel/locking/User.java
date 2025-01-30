package be.niedel.locking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "lockuser")
public class User {

    @Id
    private Long id;

    private boolean status;

    protected User() {
    }

    public User(final long id) {
        this.id = id;
        status = false;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User: ");
        sb.append(id);
        sb.append(" = ").append(status);;
        return sb.toString();
    }
}
