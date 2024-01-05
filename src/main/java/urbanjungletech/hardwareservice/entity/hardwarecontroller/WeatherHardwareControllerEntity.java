package urbanjungletech.hardwareservice.entity.hardwarecontroller;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.entity.credentials.TokenCredentialsEntity;

@Entity
public class WeatherHardwareControllerEntity extends HardwareControllerEntity{
    @OneToOne
    private CredentialsEntity credentials;

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
