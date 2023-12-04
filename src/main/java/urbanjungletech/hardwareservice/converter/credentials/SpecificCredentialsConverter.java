package urbanjungletech.hardwareservice.converter.credentials;

import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface SpecificCredentialsConverter<Model extends Credentials,
        Entity extends CredentialsEntity> {
    Model toModel(Entity entity);
    Entity createEntity(Model model);
    void fillEntity(Entity entity, Model model);
}
