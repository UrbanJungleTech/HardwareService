package urbanjungletech.hardwareservice.addition.implementation.hardwarecontroller;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.implementation.connectiondetails.WeatherConnectionDetailsAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;

@Service
public class WeatherHardwareControllerAdditionService implements SpecificAdditionService<WeatherHardwareController> {

    private final WeatherConnectionDetailsAdditionService weatherConnectionDetailsAdditionService;

    public WeatherHardwareControllerAdditionService(WeatherConnectionDetailsAdditionService weatherConnectionDetailsAdditionService) {
        this.weatherConnectionDetailsAdditionService = weatherConnectionDetailsAdditionService;
    }

    @Override
    public void create(WeatherHardwareController hardwareController) {
        WeatherConnectionDetails weatherConnectionDetails = this.weatherConnectionDetailsAdditionService.create(hardwareController.getConnectionDetails());
        hardwareController.setConnectionDetails(weatherConnectionDetails);
    }

    @Override
    public void delete(long id) {
        this.weatherConnectionDetailsAdditionService.delete(id);
    }

    @Override
    public void update(long id, WeatherHardwareController routerModel) {
        this.weatherConnectionDetailsAdditionService.update(id, routerModel.getConnectionDetails());
    }
}
