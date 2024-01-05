package urbanjungletech.hardwareservice.addition.implementation.hardwarecontroller;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;

@Service
public class WeatherHardwareControllerAdditionService implements SpecificAdditionService<WeatherHardwareController> {

    private CredentialsAdditionService credentialsAdditionService;

    public WeatherHardwareControllerAdditionService(CredentialsAdditionService credentialsAdditionService){
        this.credentialsAdditionService = credentialsAdditionService;
    }

    @Override
    public void create(WeatherHardwareController hardwareController) {
        Credentials credentials = this.credentialsAdditionService.create(hardwareController.getCredentials());
        hardwareController.setCredentials(credentials);
    }

    @Override
    public void delete(long id) {
        this.credentialsAdditionService.delete(id);
    }

    @Override
    public void update(long id, WeatherHardwareController routerModel) {
        Credentials credentials = this.credentialsAdditionService.update(id, routerModel.getCredentials());
        routerModel.setCredentials(credentials);
    }
}
