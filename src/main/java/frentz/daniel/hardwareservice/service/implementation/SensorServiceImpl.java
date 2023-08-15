package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.converter.SensorConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.model.SensorReading;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import frentz.daniel.hardwareservice.service.SensorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class SensorServiceImpl implements SensorService {

    private final HardwareControllerDAO hardwareControllerDAO;
    private final ControllerCommunicationService controllerCommunicationService;
    private final HardwareControllerRepository hardwareControllerRepository;
    private final ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private final SensorDAO sensorDAO;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private final SensorConverter sensorConverter;

    public SensorServiceImpl(HardwareControllerRepository hardwareControllerRepository,
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
        long[] sensorPorts = sensors.stream().flatMapToLong((sensor) -> {
            return LongStream.of(sensor.getPort());
        }).toArray();
        return this.controllerCommunicationService.getAverageSensorReading(hardwareControllerSerialNumber, sensorPorts);
    }

    @Override
    public ScheduledSensorReading createScheduledReading(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingDAO.create(scheduledSensorReading);
        return this.scheduledSensorReadingConverter.toModel(scheduledSensorReadingEntity);
    }

    @Override
    public Sensor getSensor(String serialNumber, int hardwarePort) {
        SensorEntity sensorEntity = this.sensorDAO.getSensorByPort(serialNumber, hardwarePort);
        return this.sensorConverter.toModel(sensorEntity);
    }

    @Override
    public Sensor getSensor(long sensorId){
        SensorEntity sensorEntity = this.sensorDAO.getSensor(sensorId);
        return this.sensorConverter.toModel(sensorEntity);
    }
}
