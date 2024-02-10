package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingRouterDAO;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.repository.SensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingRouterRepository;
import urbanjungletech.hardwareservice.service.query.SensorReadingRouterQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorReadingRouterQueryServiceImpl implements SensorReadingRouterQueryService {

    private final SensorReadingRouterRepository sensorReadingRouterRepository;
    private final SensorReadingRouterConverter sensorReadingRouterConverter;
    private final ExceptionService exceptionService;

    public SensorReadingRouterQueryServiceImpl(SensorReadingRouterRepository sensorReadingRouterRepository,
                                               SensorReadingRouterConverter sensorReadingRouterConverter,
                                               ExceptionService exceptionService) {
        this.sensorReadingRouterConverter = sensorReadingRouterConverter;
        this.sensorReadingRouterRepository = sensorReadingRouterRepository;
        this.exceptionService = exceptionService;
    }
    @Override
    public SensorReadingRouter getSensorReadingRouter(Long id) {
        SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterRepository.findById(id)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(SensorReadingRouterEntity.class, id));
        return this.sensorReadingRouterConverter.toModel(sensorReadingRouterEntity);
    }
}
