import java.io.Serializable;

public class Role implements Serializable {
    private String role;

    public Role(String Role) {
        this.role = Role;
    }

    public String getRole() {
        return this.role;
    }
}
