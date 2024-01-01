package urbanjungletech.hardwareservice.addition.implementation;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

import java.util.List;

@Service
public class CredentialsAdditionServiceImpl implements CredentialsAdditionService {

    private final CredentialsDAO credentialsDAO;
    private final CredentialsRetrievalService credentialsRetrievalService;
    private final CredentialsConverter credentialsConverter;

    public CredentialsAdditionServiceImpl(CredentialsDAO credentialsDAO,
                                          CredentialsRetrievalService credentialsRetrievalService,
                                          CredentialsConverter credentialsConverter) {
        this.credentialsDAO = credentialsDAO;
        this.credentialsRetrievalService = credentialsRetrievalService;
        this.credentialsConverter = credentialsConverter;
    }
    @Override
    public Credentials create(Credentials credentials) {
        Credentials convertedCredentials = this.credentialsRetrievalService.persistCredentials(credentials);
        CredentialsEntity credentialsEntity = this.credentialsDAO.createCredentials(convertedCredentials);
        Credentials result = this.credentialsConverter.toModel(credentialsEntity);
        return result;
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public Credentials update(long id, Credentials credentials) {
        return null;
    }

    @Override
    public List<Credentials> updateList(List<Credentials> models) {
        return null;
    }
}
