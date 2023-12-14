package urbanjungletech.hardwareservice.converter.credentials.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

import java.util.Map;

@Service
public class CredentialsConverterProxy implements CredentialsConverter {

    private final Map<Class, SpecificCredentialsConverter> specificCredentialsConverterMap;

    public CredentialsConverterProxy(Map<Class, SpecificCredentialsConverter> specificCredentialsConverterMap) {
        this.specificCredentialsConverterMap = specificCredentialsConverterMap;
    }

    @Override
    public Credentials toModel(CredentialsEntity credentialsEntity) {
        SpecificCredentialsConverter specificCredentialsConverter = this.specificCredentialsConverterMap.get(credentialsEntity.getClass());
        Credentials result = specificCredentialsConverter.toModel(credentialsEntity);
        result.setType(credentialsEntity.getType());
        result.setId(credentialsEntity.getId());
        return result;
    }

    @Override
    public void fillEntity(CredentialsEntity entity, Credentials model) {
        entity.setType(model.getType());
        SpecificCredentialsConverter specificCredentialsConverter = this.specificCredentialsConverterMap.get(model.getClass());
        specificCredentialsConverter.fillEntity(entity, model);
    }

    @Override
    public CredentialsEntity createEntity(Credentials model) {
        SpecificCredentialsConverter specificCredentialsConverter = this.specificCredentialsConverterMap.get(model.getClass());
        CredentialsEntity result = specificCredentialsConverter.createEntity(model);
        this.fillEntity(result, model);
        return result;
    }
}
