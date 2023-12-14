package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SensorDAOImpl implements SensorDAO {
    private final SensorRepository sensorRepository;
    private final HardwareControllerRepository hardwareControllerRepository;
    private final ExceptionService exceptionService;
    private final SensorConverter sensorConverter;


    public SensorDAOImpl(SensorRepository sensorRepository,
                         HardwareControllerRepository hardwareControllerRepository,
                         ExceptionService exceptionService,
                         SensorConverter sensorConverter){
        this.sensorRepository = sensorRepository;
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.exceptionService = exceptionService;
        this.sensorConverter = sensorConverter;
    }

    @Override
    public SensorEntity addSensor(Sensor sensor) {
        SensorEntity sensorEntity = new SensorEntity();
        sensorConverter.fillEntity(sensorEntity, sensor);
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(sensor.getHardwareControllerId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareControllerEntity.class, sensor.getHardwareControllerId());
        });
        sensorEntity.setHardwareController(hardwareControllerEntity);
        sensorEntity = this.sensorRepository.save(sensorEntity);
        hardwareControllerEntity.getSensors().add(sensorEntity);
        this.hardwareControllerRepository.save(hardwareControllerEntity);
        return sensorEntity;
    }

    @Override
    public SensorEntity getSensor(long sensorId) {
        SensorEntity sensorEntity = this.sensorRepository.findById(sensorId).orElseThrow(() ->{
            return this.exceptionService.createNotFoundException(SensorEntity.class, sensorId);
        });
        return sensorEntity;
    }

    @Override
    public void delete(long sensorId) {
        SensorEntity sensorEntity = this.sensorRepository.findById(sensorId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(SensorEntity.class, sensorId);
        });
        HardwareControllerEntity hardwareControllerEntity = sensorEntity.getHardwareController();
        hardwareControllerEntity.getSensors().remove(sensorEntity);
        this.hardwareControllerRepository.save(hardwareControllerEntity);
        this.sensorRepository.deleteById(sensorId);
    }

    @Override
    public SensorEntity getSensorByPort(String serialNumber, String port) {
        SensorEntity sensorEntity = this.sensorRepository.findByHardwareControllerSerialNumberAndPort(serialNumber, port).orElseThrow(() -> {
            throw this.exceptionService.createNotFoundException(SensorEntity.class, port);
        });
        return sensorEntity;
    }

    @Override
    public SensorEntity updateSensor(Sensor sensor) {
        SensorEntity sensorEntity = this.sensorRepository.findById(sensor.getId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(SensorEntity.class, sensor.getId());
        });
        this.sensorConverter.fillEntity(sensorEntity, sensor);
        this.sensorRepository.save(sensorEntity);
        return sensorEntity;
    }

    @Override
    public List<SensorEntity> findByHardwareControllerIdAndSensorType(long hardwareControllerId, String sensorType) {
        return this.sensorRepository.findByHardwareControllerIdAndSensorType(hardwareControllerId, sensorType);
    }

    @Override
    public List<SensorEntity> getSensorsByHardwareControllerId(long hardwareControllerId) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardwareControllerId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardwareControllerId);
        });
        return hardwareControllerEntity.getSensors();
    }

}
