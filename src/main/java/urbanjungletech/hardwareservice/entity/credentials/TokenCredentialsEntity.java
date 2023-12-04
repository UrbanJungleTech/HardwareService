package urbanjungletech.hardwareservice.entity.credentials;

import jakarta.persistence.Entity;

@Entity
public class TokenCredentialsEntity extends CredentialsEntity{
    private String tokenValue;
    private String url;

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String token) {
        this.tokenValue = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
