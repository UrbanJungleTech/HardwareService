package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.ConnectionDetailsConverter;
import urbanjungletech.hardwareservice.dao.ConnectionDetailsDAO;
import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;
import urbanjungletech.hardwareservice.repository.ConnectionDetailsRepository;
import urbanjungletech.hardwareservice.repository.CredentialsRepository;
import urbanjungletech.hardwareservice.service.query.CredentialsQueryService;

@Service
public class ConnectionDetailsDAOImpl implements ConnectionDetailsDAO {
    private final ConnectionDetailsRepository connectionDetailsRepository;
    private final ConnectionDetailsConverter connectionDetailsConverter;
    private final CredentialsRepository credentialsRepository;
    public ConnectionDetailsDAOImpl(ConnectionDetailsRepository connectionDetailsRepository,
                                    ConnectionDetailsConverter connectionDetailsConverter,
                                    CredentialsRepository credentialsRepository) {
        this.connectionDetailsRepository = connectionDetailsRepository;
        this.connectionDetailsConverter = connectionDetailsConverter;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public ConnectionDetailsEntity create(ConnectionDetails connectionDetails) {
        ConnectionDetailsEntity connectionDetailsEntity = this.connectionDetailsConverter.createEntity(connectionDetails);
        connectionDetailsEntity = this.connectionDetailsRepository.save(connectionDetailsEntity);
        return connectionDetailsEntity;
    }

    @Override
    public ConnectionDetailsEntity update(ConnectionDetails connectionDetails) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
