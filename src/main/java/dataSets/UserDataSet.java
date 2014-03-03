package dataSets;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDataSet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public UserDataSet(long id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public UserDataSet(String username, String password) {
        this(-1, username, password);
    }
    public UserDataSet() {
        this(-1, "", "");
    }

    public String getPassword() { return password; }
    public String getUsername() {
        return username;
    }
    public long getId() {
        return id;
    }
    public void setPassword(String password) { this.password = password; }
    public void setUsername(String username) { this.username = username; }
    public void setId(long id) { this.id = id; }
}
