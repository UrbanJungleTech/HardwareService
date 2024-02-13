package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.service.query.CredentialsQueryService;

@Service
public class CredentialsQueryServiceImpl implements CredentialsQueryService {

    private final CredentialsDAO credentialsDAO;
    private final CredentialsConverter credentialsConverter;

    public CredentialsQueryServiceImpl(CredentialsDAO credentialsDAO,
                                       CredentialsConverter credentialsConverter) {
        this.credentialsDAO = credentialsDAO;
        this.credentialsConverter = credentialsConverter;
    }

    @Override
    public Credentials findById(Long id) {
        CredentialsEntity credentialsEntity = this.credentialsDAO.findById(id);
        return this.credentialsConverter.toModel(credentialsEntity);
    }
}
