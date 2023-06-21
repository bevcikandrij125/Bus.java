package bean;
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private int admin_level;
    private String email;
    private String password;

    public User() {
        super();
    }

    public User(int id, String firstname, String lastname, int admin_level, String email, String password) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.admin_level = admin_level;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    public boolean isAdmin() {
        return admin_level > 0;
    }
    public int getAdminLevel() {
        return admin_level;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}