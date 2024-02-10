package urbanjungletech.hardwareservice.addition.implementation.connectiondetails;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.connectiondetails.ConnectionDetailsConverter;
import urbanjungletech.hardwareservice.dao.ConnectionDetailsDAO;
import urbanjungletech.hardwareservice.entity.connectiondetails.WeatherConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherConnectionDetailsAdditionService implements AdditionService<WeatherConnectionDetails> {

    private final CredentialsAdditionService credentialsAdditionService;
    private final ConnectionDetailsDAO connectionDetailsDAO;
    private final ConnectionDetailsConverter connectionDetailsConverter;

    public WeatherConnectionDetailsAdditionService(CredentialsAdditionService credentialsAdditionService,
                                                   ConnectionDetailsDAO connectionDetailsDAO,
                                                   ConnectionDetailsConverter connectionDetailsConverter) {
        this.credentialsAdditionService = credentialsAdditionService;
        this.connectionDetailsDAO = connectionDetailsDAO;
        this.connectionDetailsConverter = connectionDetailsConverter;
    }


    @Override
    public WeatherConnectionDetails create(WeatherConnectionDetails connectionDetails) {
        TokenCredentials credentials = (TokenCredentials) this.credentialsAdditionService.create(connectionDetails.getCredentials());
        connectionDetails.setCredentials(credentials);
        WeatherConnectionDetailsEntity weatherConnectionDetailsEntity = (WeatherConnectionDetailsEntity) this.connectionDetailsDAO.create(connectionDetails);
        return (WeatherConnectionDetails) this.connectionDetailsConverter.toModel(weatherConnectionDetailsEntity);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public WeatherConnectionDetails update(long id, WeatherConnectionDetails connectionDetails) {
        return null;
    }

    @Override
    public List<WeatherConnectionDetails> updateList(List<WeatherConnectionDetails> models) {
        List<WeatherConnectionDetails> result = new ArrayList<>();
        for (WeatherConnectionDetails model : models) {
            if (model.getId() == null) {
                this.create(model);
                result.add(model);
            } else {
                this.update(model.getId(), model);
                result.add(model);
            }
        }
        return result;
    }

}
