package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.sensor.SensorConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorQueryServiceImpl implements SensorQueryService {

    private final HardwareControllerDAO hardwareControllerDAO;
    private final ControllerCommunicationService controllerCommunicationService;
    private final SensorDAO sensorDAO;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private final SensorConverter sensorConverter;

    public SensorQueryServiceImpl(ControllerCommunicationService controllerCommunicationService,
                                  HardwareControllerDAO hardwareControllerDAO,
                                  SensorDAO sensorDAO,
                                  ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                                  SensorConverter sensorConverter){
        this.controllerCommunicationService = controllerCommunicationService;
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.sensorDAO = sensorDAO;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.sensorConverter = sensorConverter;
    }

    @Override
    public SensorReading readSensor(long sensorId) {
        Sensor sensor = this.getSensor(sensorId);
        double reading = this.controllerCommunicationService.getSensorReading(sensor);
        SensorReading result = new SensorReading();
        result.setReading(reading);
        result.setSensorId(sensorId);
        result.setReadingTime(LocalDateTime.now());
        return result;
    }

    @Override
    public Sensor getSensor(String serialNumber, String sensorPort) {
        SensorEntity sensorEntity = this.sensorDAO.getSensorByPort(serialNumber, sensorPort);
        return this.sensorConverter.toModel(sensorEntity);
    }

    @Override
    public Sensor getSensor(long sensorId){
        SensorEntity sensorEntity = this.sensorDAO.getSensor(sensorId);
        return this.sensorConverter.toModel(sensorEntity);
    }
}
