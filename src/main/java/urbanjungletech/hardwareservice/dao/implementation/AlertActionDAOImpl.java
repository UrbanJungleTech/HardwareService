package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.dao.AlertActionDAO;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.repository.AlertActionRepository;
import urbanjungletech.hardwareservice.repository.AlertRepository;

import java.util.List;

@Service
public class AlertActionDAOImpl implements AlertActionDAO {
    private final AlertActionRepository alertActionRepository;
    private final AlertActionConverter alertActionConverter;
    private final ExceptionService exceptionService;
    private final AlertRepository alertRepository;

    public AlertActionDAOImpl(AlertActionRepository alertActionRepository,
                              AlertActionConverter alertActionConverter,
                              ExceptionService exceptionService,
                              AlertRepository alertRepository){
        this.alertActionRepository = alertActionRepository;
        this.alertActionConverter = alertActionConverter;
        this.exceptionService = exceptionService;
        this.alertRepository = alertRepository;
    }
    @Override
    public AlertActionEntity create(AlertAction alertAction) {
        AlertActionEntity alertActionEntity = this.alertActionConverter.createEntity(alertAction);
        AlertEntity alertEntity = this.alertRepository.findById(alertAction.getAlertId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertEntity.class, alertAction.getAlertId())
        );
        alertActionEntity.setAlert(alertEntity);
        AlertActionEntity result = this.alertActionRepository.save(alertActionEntity);
        this.alertActionRepository.save(alertActionEntity);
        alertEntity.getActions().add(alertActionEntity);
        this.alertRepository.save(alertEntity);
        return result;
    }

    @Override
    public void deleteAction(long actionId) {
        if(this.alertActionRepository.existsById(actionId) == false)
            throw this.exceptionService.createNotFoundException(AlertAction.class, actionId);
        this.alertActionRepository.deleteById(actionId);
    }

    @Override
    public AlertAction updateAction(AlertAction alertAction) {
        return null;
    }

    @Override
    public AlertActionEntity getAction(long actionId) {
        AlertActionEntity result = this.alertActionRepository.findById(actionId).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertActionEntity.class, actionId)
        );
        return result;
    }

    @Override
    public List<AlertActionEntity> getAllActions() {
        return this.alertActionRepository.findAll();
    }
}
