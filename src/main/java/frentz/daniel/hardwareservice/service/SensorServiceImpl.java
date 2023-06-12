package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.converter.SensorConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.client.model.SensorReading;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class SensorServiceImpl implements SensorService{

    private HardwareControllerDAO hardwareControllerDAO;
    private HardwareQueueService hardwareQueueService;
    private HardwareControllerRepository hardwareControllerRepository;
    private ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private SensorDAO sensorDAO;
    private ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private SensorConverter sensorConverter;

    public SensorServiceImpl(HardwareControllerRepository hardwareControllerRepository,
                             HardwareQueueService hardwareQueueService,
                             HardwareControllerDAO hardwareControllerDAO,
                             ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                             SensorDAO sensorDAO,
                             ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                             SensorConverter sensorConverter){
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.hardwareQueueService = hardwareQueueService;
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.sensorDAO = sensorDAO;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.sensorConverter = sensorConverter;
    }

    @Override
    public SensorReading readSensor(long sensorId) {
        SensorEntity sensor = this.sensorDAO.getSensor(sensorId);
        Double reading = this.hardwareQueueService.getSensorReading(sensor);
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
        return this.hardwareQueueService.getAverageSensorReading(hardwareControllerSerialNumber, sensorPorts);
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
