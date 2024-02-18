package urbanjungletech.hardwareservice.model.hardwarecontroller;

import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.validation.annotation.WeatherSensorTypeValidation;

public class WeatherHardwareController extends HardwareController {
    public WeatherHardwareController() {
        super();
    }
    private WeatherConnectionDetails connectionDetails;

    public WeatherConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

    public void setConnectionDetails(WeatherConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }
}
