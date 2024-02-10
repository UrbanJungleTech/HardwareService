package urbanjungletech.hardwareservice.entity.connectiondetails;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;

@Entity
public class AzureConnectionDetailsEntity extends ConnectionDetailsEntity{
    private String url;
    @ManyToOne
    private CredentialsEntity credentials;

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
