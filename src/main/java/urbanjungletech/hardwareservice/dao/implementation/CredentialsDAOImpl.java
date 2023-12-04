package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.repository.CredentialsRepository;

@Service
public class CredentialsDAOImpl implements CredentialsDAO {

    private final CredentialsRepository credentialsRepository;
    private final CredentialsConverter credentialsConverter;
    private final ExceptionService exceptionService;

    public CredentialsDAOImpl(CredentialsRepository credentialsRepository,
                              CredentialsConverter credentialsConverter,
                              ExceptionService exceptionService) {
        this.credentialsRepository = credentialsRepository;
        this.credentialsConverter = credentialsConverter;
        this.exceptionService = exceptionService;
    }
    @Override
    public CredentialsEntity createCredentials(Credentials credentials) {
        CredentialsEntity result = this.credentialsConverter.createEntity(credentials);
        this.credentialsRepository.save(result);
        return result;
    }

    @Override
    public CredentialsEntity findById(Long credentialsId) {
        CredentialsEntity credentialsEntity = this.credentialsRepository.findById(credentialsId).orElseThrow( () ->
                this.exceptionService.createNotFoundException(HardwareControllerEntity.class, credentialsId));
        return credentialsEntity;
    }
}
