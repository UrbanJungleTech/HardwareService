package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorQueryServiceImpl implements SensorQueryService {

    private final HardwareControllerDAO hardwareControllerDAO;
    private final ControllerCommunicationService controllerCommunicationService;
    private final HardwareControllerRepository hardwareControllerRepository;
    private final ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private final SensorDAO sensorDAO;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private final SensorConverter sensorConverter;

    public SensorQueryServiceImpl(HardwareControllerRepository hardwareControllerRepository,
                                  ControllerCommunicationService controllerCommunicationService,
                                  HardwareControllerDAO hardwareControllerDAO,
                                  ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                  SensorDAO sensorDAO,
                                  ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                                  SensorConverter sensorConverter){
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.controllerCommunicationService = controllerCommunicationService;
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
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
    public double readAverageSensor(long hardwareControllerId, String sensorType) {
        HardwareControllerEntity hardwareController = this.hardwareControllerDAO.getHardwareController(hardwareControllerId);
        String hardwareControllerSerialNumber = hardwareController.getSerialNumber();
        List<SensorEntity> sensors = this.sensorDAO.findByHardwareControllerIdAndSensorType(hardwareControllerId, sensorType);
        String[] sensorPorts = sensors.stream().map((sensor) -> {
            return sensor.getPort();
        }).toArray(String[]::new);
        return this.controllerCommunicationService.getAverageSensorReading(sensorPorts);
    }

    @Override
    public ScheduledSensorReading createScheduledReading(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingDAO.create(scheduledSensorReading);
        return this.scheduledSensorReadingConverter.toModel(scheduledSensorReadingEntity);
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
