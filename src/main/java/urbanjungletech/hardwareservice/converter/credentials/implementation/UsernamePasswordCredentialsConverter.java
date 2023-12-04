package urbanjungletech.hardwareservice.converter.credentials.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.entity.credentials.UsernamePasswordCredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;

@Service
public class UsernamePasswordCredentialsConverter implements SpecificCredentialsConverter<UsernamePasswordCredentials, UsernamePasswordCredentialsEntity> {
    @Override
    public UsernamePasswordCredentials toModel(UsernamePasswordCredentialsEntity entity) {
        UsernamePasswordCredentials result = new UsernamePasswordCredentials();
        result.setUsername(entity.getUsername());
        result.setPassword(entity.getPassword());
        return result;
    }

    @Override
    public UsernamePasswordCredentialsEntity createEntity(UsernamePasswordCredentials usernamePasswordCredentials) {
        UsernamePasswordCredentialsEntity result = new UsernamePasswordCredentialsEntity();
        this.fillEntity(result, usernamePasswordCredentials);
        return result;
    }

    @Override
    public void fillEntity(UsernamePasswordCredentialsEntity entity, UsernamePasswordCredentials usernamePasswordCredentials) {
        entity.setUsername(usernamePasswordCredentials.getUsername());
        entity.setPassword(usernamePasswordCredentials.getPassword());
    }
}
