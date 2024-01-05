package urbanjungletech.hardwareservice.model.hardwarecontroller;

import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;

public class WeatherHardwareController extends HardwareController {
    public WeatherHardwareController() {
        super();
    }
    private Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
