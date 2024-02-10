package urbanjungletech.hardwareservice.converter.connectiondetails.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.SpecificConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.WeatherConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.TokenCredentialsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.repository.CredentialsRepository;

@Service
public class WeatherConnectionDetailsConverter implements SpecificConnectionDetailsConverter<WeatherConnectionDetails, WeatherConnectionDetailsEntity>{
    private final CredentialsConverter credentialsConverter;
    private final CredentialsRepository credentialsRepository;

    public WeatherConnectionDetailsConverter(CredentialsConverter credentialsConverter,
                                             CredentialsRepository credentialsRepository){
        this.credentialsConverter = credentialsConverter;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public void fillEntity(WeatherConnectionDetailsEntity entity, WeatherConnectionDetails model) {
        entity.setUrl(model.getUrl());
        TokenCredentialsEntity credentialsEntity = (TokenCredentialsEntity)this.credentialsRepository.findById(model.getCredentials().getId())
                .orElseThrow(() -> new RuntimeException("Credentials not found"));
        entity.setCredentials(credentialsEntity);
    }

    @Override
    public WeatherConnectionDetails toModel(WeatherConnectionDetailsEntity entity) {
        WeatherConnectionDetails result = new WeatherConnectionDetails();
        TokenCredentials credentials = (TokenCredentials)this.credentialsConverter.toModel(entity.getCredentials());
        result.setCredentials(credentials);
        result.setUrl(entity.getUrl());
        result.setId(entity.getId());
        return result;
    }

    @Override
    public WeatherConnectionDetailsEntity createEntity(WeatherConnectionDetails model) {
        WeatherConnectionDetailsEntity result = new WeatherConnectionDetailsEntity();
        this.fillEntity(result, model);
        return result;
    }
}
