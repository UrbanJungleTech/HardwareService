package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.WeatherHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;

@Service
public class WeatherHardwareControllerConverter implements SpecificHardwareControllerConverter<WeatherHardwareController, WeatherHardwareControllerEntity> {

    private final CredentialsConverter credentialsConverter;
    private final CredentialsDAO credentialsDAO;
    public WeatherHardwareControllerConverter(CredentialsConverter credentialsConverter,
                                              CredentialsDAO credentialsDAO){
        this.credentialsConverter = credentialsConverter;
        this.credentialsDAO = credentialsDAO;
    }

    @Override
    public void fillEntity(WeatherHardwareControllerEntity hardwareControllerEntity, WeatherHardwareController hardwareController) {
        CredentialsEntity credentials = this.credentialsDAO.findById(hardwareController.getCredentials().getId());
        hardwareControllerEntity.setCredentials(credentials);
    }

    @Override
    public WeatherHardwareController toModel(WeatherHardwareControllerEntity hardwareControllerEntity) {
        WeatherHardwareController result = new WeatherHardwareController();
        Credentials credentials = this.credentialsConverter.toModel(hardwareControllerEntity.getCredentials());
        result.setCredentials(credentials);
        return result;
    }

    @Override
    public WeatherHardwareControllerEntity createEntity(WeatherHardwareController hardwareController) {
        WeatherHardwareControllerEntity result = new WeatherHardwareControllerEntity();
        return result;
    }
}
