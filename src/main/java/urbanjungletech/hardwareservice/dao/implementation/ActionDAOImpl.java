package urbanjungletech.hardwareservice.dao.implementation;

import urbanjungletech.hardwareservice.converter.ActionConverter;
import urbanjungletech.hardwareservice.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.Action;
import urbanjungletech.hardwareservice.repository.ActionRepository;
import urbanjungletech.hardwareservice.dao.ActionDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.repository.SensorReadingAlertRepository;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionDAOImpl implements ActionDAO {
    private ActionRepository actionRepository;
    private ActionConverter actionConverter;
    private ExceptionService exceptionService;
    private SensorReadingAlertRepository sensorReadingAlertRepository;

    public ActionDAOImpl(ActionRepository actionRepository,
                         ActionConverter actionConverter,
                         ExceptionService exceptionService,
                         SensorReadingAlertRepository sensorReadingAlertRepository){
        this.actionRepository = actionRepository;
        this.actionConverter = actionConverter;
        this.exceptionService = exceptionService;
        this.sensorReadingAlertRepository = sensorReadingAlertRepository;
    }
    @Override
    public ActionEntity create(Action action) {
        ActionEntity actionEntity = this.actionConverter.createEntity(action);
        SensorReadingAlertEntity sensorReadingAlertEntity = this.sensorReadingAlertRepository.findById(action.getSensorReadingAlertId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(SensorReadingAlertEntity.class, action.getSensorReadingAlertId())
        );
        actionEntity.setSensorReadingAlert(sensorReadingAlertEntity);
        ActionEntity result = this.actionRepository.save(actionEntity);
        this.actionRepository.save(actionEntity);
        sensorReadingAlertEntity.getActions().add(actionEntity);
        this.sensorReadingAlertRepository.save(sensorReadingAlertEntity);
        return result;
    }

    @Override
    public void deleteAction(long actionId) {
        if(this.actionRepository.existsById(actionId) == false)
            throw this.exceptionService.createNotFoundException(Action.class, actionId);
        this.actionRepository.deleteById(actionId);
    }

    @Override
    public Action updateAction(Action action) {
        return null;
    }

    @Override
    public ActionEntity getAction(long actionId) {
        ActionEntity result = this.actionRepository.findById(actionId).orElseThrow(
                () -> this.exceptionService.createNotFoundException(ActionEntity.class, actionId)
        );
        return result;
    }

    @Override
    public List<ActionEntity> getAllActions() {
        return this.actionRepository.findAll();
    }
}
