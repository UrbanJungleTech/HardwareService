package urbanjungletech.hardwareservice.entity.hardwarecontroller;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import urbanjungletech.hardwareservice.entity.connectiondetails.WeatherConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.entity.credentials.TokenCredentialsEntity;

@Entity
public class WeatherHardwareControllerEntity extends HardwareControllerEntity{
    @ManyToOne
    private WeatherConnectionDetailsEntity connectionDetails;

    public WeatherConnectionDetailsEntity getConnectionDetails() {
        return connectionDetails;
    }

    public void setConnectionDetails(WeatherConnectionDetailsEntity connectionDetails) {
        this.connectionDetails = connectionDetails;
    }
}
