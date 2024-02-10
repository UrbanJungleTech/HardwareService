package urbanjungletech.hardwareservice.converter.connectiondetails.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.SpecificConnectionDetailsConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.AzureConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.repository.CredentialsRepository;
import urbanjungletech.hardwareservice.service.query.CredentialsQueryService;

@Service
public class SpecificAzureConnectionDetailsConverter implements SpecificConnectionDetailsConverter<AzureConnectionDetails, AzureConnectionDetailsEntity> {

    private CredentialsRepository credentialsRepository;
    private CredentialsQueryService credentialsQueryService;

    public SpecificAzureConnectionDetailsConverter(CredentialsRepository credentialsRepository,
                                                   CredentialsQueryService credentialsQueryService){
        this.credentialsRepository = credentialsRepository;
        this.credentialsQueryService = credentialsQueryService;
    }
    @Override
    public AzureConnectionDetails toModel(AzureConnectionDetailsEntity entity) {
        AzureConnectionDetails result = new AzureConnectionDetails();
        result.setUrl(entity.getUrl());
        result.setCredentials(credentialsQueryService.findById(entity.getCredentials().getId()));
        return result;
    }

    @Override
    public AzureConnectionDetailsEntity createEntity(AzureConnectionDetails connectionDetails) {
        AzureConnectionDetailsEntity result = new AzureConnectionDetailsEntity();
        this.fillEntity(result, connectionDetails);
        return result;
    }

    @Override
    public void fillEntity(AzureConnectionDetailsEntity entity, AzureConnectionDetails connectionDetails) {
        entity.setUrl(connectionDetails.getUrl());
        CredentialsEntity credentialsEntity = this.credentialsRepository.findById(connectionDetails.getCredentials().getId()).orElseThrow(() -> new RuntimeException("Credentials not found"));
        entity.setCredentials(credentialsEntity);
    }
}
