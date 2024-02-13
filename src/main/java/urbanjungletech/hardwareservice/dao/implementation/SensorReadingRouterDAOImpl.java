package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingRouterDAO;
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
        SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterConverter.createEntity(sensorReadingRouter);
        sensorReadingRouterEntity = this.sensorReadingRouterRepository.save(sensorReadingRouterEntity);
        return sensorReadingRouterEntity;
    }

    @Override
    public SensorReadingRouterEntity update(SensorReadingRouter sensorReadingRouter) {
        SensorReadingRouterEntity result = this.sensorReadingRouterRepository
                .findById(sensorReadingRouter.getId()).orElseThrow(
                        () -> this.exceptionService.createNotFoundException(SensorReadingRouterEntity.class, sensorReadingRouter.getId())
                );
        this.sensorReadingRouterConverter.fillEntity(result, sensorReadingRouter);
        return this.sensorReadingRouterRepository.save(result);
    }

    @Override
    public SensorReadingRouterEntity getById(Long id) {
        return this.sensorReadingRouterRepository.findById(id).orElseThrow(
                () -> this.exceptionService.createNotFoundException(SensorReadingRouterEntity.class, id)
        );
    }

    @Override
    public void delete(long id) {
        SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterRepository.findById(id).orElseThrow(
                () -> this.exceptionService.createNotFoundException(SensorReadingRouterEntity.class, id)
        );
        this.sensorReadingRouterRepository.delete(sensorReadingRouterEntity);
    }
}
