package urbanjungletech.hardwareservice.entity.connectiondetails;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.credentials.TokenCredentialsEntity;

@Entity
public class WeatherConnectionDetailsEntity extends ConnectionDetailsEntity {
    private String url;
    @ManyToOne
    private TokenCredentialsEntity credentials;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TokenCredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(TokenCredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
