package urbanjungletech.hardwareservice.converter.sensor.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.sensor.SensorConverter;
import urbanjungletech.hardwareservice.converter.sensor.SpecificSensorConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingRouterDAO;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SensorConverterImpl implements SensorConverter {

    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private final SensorReadingRouterDAO sensorReadingRouterDAO;
    private final SensorReadingRouterConverter sensorReadingRouterConverter;
    private final Map<Class, SpecificSensorConverter> specificSensorConverters;

    public SensorConverterImpl(ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                               SensorReadingRouterDAO sensorReadingRouterDAO,
                               SensorReadingRouterConverter sensorReadingRouterConverter,
                               Map<Class, SpecificSensorConverter> specificSensorConverters){
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.sensorReadingRouterDAO = sensorReadingRouterDAO;
        this.sensorReadingRouterConverter = sensorReadingRouterConverter;
        this.specificSensorConverters = specificSensorConverters;
    }

    @Override
    public Sensor toModel(SensorEntity sensorEntity) {
        Sensor result = this.specificSensorConverters.get(sensorEntity.getClass()).toModel(sensorEntity);
        result.setId(sensorEntity.getId());
        result.setName(sensorEntity.getName());
        result.setPort(sensorEntity.getPort());
        result.setHardwareControllerId(sensorEntity.getHardwareController().getId());
        List<ScheduledSensorReading> scheduledSensorReadings = this.scheduledSensorReadingConverter.toModels(sensorEntity.getScheduledSensorReadings());
        result.setScheduledSensorReadings(scheduledSensorReadings);
        List<SensorReadingRouter> sensorReadingRouters = sensorEntity.getSensorReadingRouters()
                .stream()
                .map(sensorReadingRouterEntity -> this.sensorReadingRouterConverter.toModel(sensorReadingRouterEntity)).collect(Collectors.toList());
        result.setSensorReadingRouters(sensorReadingRouters);
        return result;
    }

    @Override
    public List<Sensor> toModels(List<SensorEntity> sensorEntities) {
        if(sensorEntities != null) {
            return sensorEntities.stream().map((this::toModel)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void fillEntity(SensorEntity sensorEntity, Sensor sensor) {
        sensorEntity.setPort(sensor.getPort());
        sensorEntity.setName(sensor.getName());
        sensor.getSensorReadingRouters().forEach(sensorReadingRouter -> {
            SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterDAO.getById(sensorReadingRouter.getId());
            sensorEntity.getSensorReadingRouters().add(sensorReadingRouterEntity);
        });
    }

    @Override
    public SensorEntity createEntity(Sensor sensor) {
        return this.specificSensorConverters.get(sensor.getClass()).createEntity(sensor);
    }
}
