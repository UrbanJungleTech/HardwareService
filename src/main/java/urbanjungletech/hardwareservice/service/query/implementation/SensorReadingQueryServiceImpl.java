package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.SensorReadingConverter;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.repository.SensorReadingRepository;
import urbanjungletech.hardwareservice.service.query.SensorReadingQueryService;

@Service
public class SensorReadingQueryServiceImpl implements SensorReadingQueryService {

    private final SensorReadingRepository sensorReadingRepository;
    private final ExceptionService exceptionService;
    private final SensorReadingConverter sensorReadingConverter;

    public SensorReadingQueryServiceImpl(SensorReadingRepository sensorReadingRepository,
                                         ExceptionService exceptionService,
                                         SensorReadingConverter sensorReadingConverter) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.exceptionService = exceptionService;
        this.sensorReadingConverter = sensorReadingConverter;
    }

    @Override
    public SensorReading findSensorReadingBySensorId(Long readingId) {
        SensorReadingEntity sensorReadingEntity = this.sensorReadingRepository.findById(readingId)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(SensorReading.class, readingId));
        SensorReading result = this.sensorReadingConverter.toModel(sensorReadingEntity);
        return result;
    }

    @Override
    public SensorReading findById(Long id) {
        return this.sensorReadingRepository.findById(id)
                .map(this.sensorReadingConverter::toModel)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(SensorReading.class, id));
    }
}
