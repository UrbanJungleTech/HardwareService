package urbanjungletech.hardwareservice.addition.implementation.connectiondetails;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.converter.connectiondetails.ConnectionDetailsConverter;
import urbanjungletech.hardwareservice.dao.ConnectionDetailsDAO;
import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

import java.util.List;

@Service
public class DatabaseConnectionDetailsAdditionService implements AdditionService<DatabaseConnectionDetails> {

    private final CredentialsAdditionService credentialsAdditionService;
    private final ConnectionDetailsDAO connectionDetailsDAO;
    private final ConnectionDetailsConverter connectionDetailsConverter;

    public DatabaseConnectionDetailsAdditionService(CredentialsAdditionService credentialsAdditionService,
                                                    ConnectionDetailsDAO connectionDetailsDAO,
                                                    ConnectionDetailsConverter connectionDetailsConverter){
        this.credentialsAdditionService = credentialsAdditionService;
        this.connectionDetailsDAO = connectionDetailsDAO;
        this.connectionDetailsConverter = connectionDetailsConverter;
    }

    @Override
    public DatabaseConnectionDetails create(DatabaseConnectionDetails connectionDetails) {
        Credentials credentials = this.credentialsAdditionService.create(connectionDetails.getCredentials());
        connectionDetails.setCredentials(credentials);
        ConnectionDetailsEntity connectionDetailsEntity = this.connectionDetailsDAO.create(connectionDetails);
        return (DatabaseConnectionDetails) this.connectionDetailsConverter.toModel(connectionDetailsEntity);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public DatabaseConnectionDetails update(long id, DatabaseConnectionDetails connectionDetails) {
        return null;
    }

    @Override
    public List<DatabaseConnectionDetails> updateList(List<DatabaseConnectionDetails> models) {
        return null;
    }
}
