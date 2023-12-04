package urbanjungletech.hardwareservice.entity.credentials;

import jakarta.persistence.Entity;

@Entity
public class UsernamePasswordCredentialsEntity extends CredentialsEntity{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
