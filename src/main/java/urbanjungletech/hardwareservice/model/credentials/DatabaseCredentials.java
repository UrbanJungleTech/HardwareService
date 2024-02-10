package urbanjungletech.hardwareservice.model.credentials;

import java.util.Objects;

public class DatabaseCredentials extends Credentials{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return false;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseCredentials that = (DatabaseCredentials) o;
                return that.getUsername().equals(this.getUsername()) &&
                that.getPassword().equals(this.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
