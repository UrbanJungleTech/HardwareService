package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingRouterDAO;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.repository.ScheduledSensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingRouterRepository;

@Service
public class SensorReadingRouterDAOImpl implements SensorReadingRouterDAO {
    private SensorReadingRouterRepository sensorReadingRouterRepository;
    private SensorReadingRouterConverter sensorReadingRouterConverter;
    private ScheduledSensorReadingRepository scheduledSensorReadingRepository;
    private ExceptionService exceptionService;

    public SensorReadingRouterDAOImpl(SensorReadingRouterRepository sensorReadingRouterRepository,
                                      SensorReadingRouterConverter sensorReadingRouterConverter,
                                      ScheduledSensorReadingRepository scheduledSensorReadingRepository,
                                      ExceptionService exceptionService){
        this.sensorReadingRouterRepository = sensorReadingRouterRepository;
        this.sensorReadingRouterConverter = sensorReadingRouterConverter;
        this.scheduledSensorReadingRepository = scheduledSensorReadingRepository;
        this.exceptionService = exceptionService;
    }
    @Override
    public SensorReadingRouterEntity create(SensorReadingRouter sensorReadingRouter) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingRepository
                .findById(sensorReadingRouter.getScheduledSensorReadingId()).orElseThrow(
                        () -> this.exceptionService.createNotFoundException(ScheduledSensorReadingEntity.class, sensorReadingRouter.getScheduledSensorReadingId())
                );
        SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterConverter.createEntity(sensorReadingRouter);
        sensorReadingRouterEntity.setScheduledSensorReadingEntity(scheduledSensorReadingEntity);
        sensorReadingRouterEntity = this.sensorReadingRouterRepository.save(sensorReadingRouterEntity);
        scheduledSensorReadingEntity.getRouters().add(sensorReadingRouterEntity);
        this.scheduledSensorReadingRepository.save(scheduledSensorReadingEntity);
        return sensorReadingRouterEntity;
    }

    @Override
    public SensorReadingRouterEntity update(SensorReadingRouter sensorReadingRouter) {
        return null;
    }
}
