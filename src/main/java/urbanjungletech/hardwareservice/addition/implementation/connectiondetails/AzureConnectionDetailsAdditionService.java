package urbanjungletech.hardwareservice.addition.implementation.connectiondetails;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.connectiondetails.ConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.connectiondetails.implementation.SpecificAzureConnectionDetailsConverter;
import urbanjungletech.hardwareservice.dao.ConnectionDetailsDAO;
import urbanjungletech.hardwareservice.entity.connectiondetails.AzureConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

import java.util.List;

@Service
public class AzureConnectionDetailsAdditionService implements AdditionService<AzureConnectionDetails> {

    private final CredentialsAdditionService credentialsAdditionService;
    private final ConnectionDetailsDAO connectionDetailsDAO;
    private final ConnectionDetailsConverter specificAzureConnectionDetailsConverter;

    public AzureConnectionDetailsAdditionService(CredentialsAdditionService credentialsAdditionService,
                                                ConnectionDetailsDAO connectionDetailsDAO,
                                                ConnectionDetailsConverter specificAzureConnectionDetailsConverter){
        this.credentialsAdditionService = credentialsAdditionService;
        this.connectionDetailsDAO = connectionDetailsDAO;
        this.specificAzureConnectionDetailsConverter = specificAzureConnectionDetailsConverter;
    }
    @Override
    public AzureConnectionDetails create(AzureConnectionDetails azureConnectionDetails) {
        Credentials credentials = this.credentialsAdditionService.create(azureConnectionDetails.getCredentials());
        azureConnectionDetails.setCredentials(credentials);
        AzureConnectionDetailsEntity connectionDetailsEntity = (AzureConnectionDetailsEntity) this.connectionDetailsDAO.create(azureConnectionDetails);
        return (AzureConnectionDetails) this.specificAzureConnectionDetailsConverter.toModel(connectionDetailsEntity);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public AzureConnectionDetails update(long id, AzureConnectionDetails azureConnectionDetails) {
        return null;
    }

    @Override
    public List<AzureConnectionDetails> updateList(List<AzureConnectionDetails> models) {
        return null;
    }
}
