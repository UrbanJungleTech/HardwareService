package frentz.daniel.controller.service;

import frentz.daniel.controller.converter.HardwareControllerConverter;
import frentz.daniel.controller.converter.HardwareConverter;
import frentz.daniel.controller.converter.SensorConverter;
import frentz.daniel.controller.entity.*;
import frentz.daniel.controller.repository.HardwareControllerRepository;
import frentz.daniel.controllerclient.model.*;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HardwareControllerServiceImpl implements HardwareControllerService{

    private HardwareControllerRepository hardwareControllerRepository;
    private HardwareControllerConverter hardwareControllerConverter;
    private HardwareService hardwareService;
    private HardwareConverter hardwareConverter;
    private HardwareQueueService queueService;
    private SensorService sensorService;
    private SensorConverter sensorConverter;
    private SensorQueueService sensorQueueService;

    public HardwareControllerServiceImpl(HardwareControllerRepository hardwareControllerRepository,
                                         HardwareControllerConverter hardwareControllerConverter,
                                         HardwareService hardwareService,
                                         HardwareConverter hardwareConverter,
                                         HardwareQueueService queueService,
                                         SensorService sensorService,
                                         SensorConverter sensorConverter,
                                         SensorQueueService sensorQueueService){
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.hardwareService = hardwareService;
        this.hardwareConverter = hardwareConverter;
        this.queueService = queueService;
        this.sensorService = sensorService;
        this.sensorConverter = sensorConverter;
        this.sensorQueueService = sensorQueueService;
    }

    @Override
    public HardwareController createHardwareController(HardwareController hardwareController) {
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareControllerEntity.setName(hardwareController.getName());
        hardwareControllerEntity.setSerialNumber(hardwareController.getSerialNumber());
        hardwareControllerEntity = this.hardwareControllerRepository.save(hardwareControllerEntity);
        for(Hardware hardware : hardwareController.getHardware()){
            HardwareEntity hardwareEntity = hardwareService.createAndSaveHardware(hardware, hardwareControllerEntity);
            hardwareControllerEntity.getHardware().add(hardwareEntity);
        }
        this.queueService.createHardwareStateChangeQueue(hardwareController.getSerialNumber());
        this.sensorQueueService.createSensorReadingQueue(hardwareControllerEntity.getSerialNumber());
        return this.hardwareControllerConverter.toModel(hardwareControllerEntity);
    }


    @Override
    @Transactional
    public Hardware addHardware(long hardwareControllerId, Hardware hardware) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardwareControllerId).orElseGet(null);
        HardwareEntity hardwareEntity = this.hardwareService.createAndSaveHardware(hardware, hardwareControllerEntity);
        return this.hardwareConverter.toModel(hardwareEntity);
    }

    @Override
    @Transactional
    public Sensor addSensor(long hardwareControllerId, Sensor sensor) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardwareControllerId).orElseGet(null);
        SensorEntity sensorEntity = this.sensorService.addAndCreateSensor(sensor, hardwareControllerEntity);
        return this.sensorConverter.toModel(sensorEntity);
    }

    @Override
    public List<HardwareController> getAllHardware() {
        List<HardwareControllerEntity> entities = this.hardwareControllerRepository.findAll();
        return this.hardwareControllerConverter.toModels(entities);
    }

    @Override
    public HardwareController setHardwareState(long hardwareControllerId, long port, HardwareState hardwareState) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardwareControllerId).orElseGet(null);
        HardwareEntity hardwareEntity = hardwareControllerEntity.getHardware().stream().filter((hardware) -> {
            return hardware.getPort() == port;
        }).findFirst().orElseGet(null);
        this.hardwareService.setDesiredState(hardwareEntity, hardwareState);
        this.queueService.sendStateToController(hardwareControllerEntity, port, hardwareState);
        return this.hardwareControllerConverter.toModel(hardwareControllerEntity);
    }

    @Override
    public HardwareController getHardwareController(long hardwareControllerId) {
        HardwareControllerEntity entity = this.hardwareControllerRepository.findById(hardwareControllerId).orElseGet(null);
        return this.hardwareControllerConverter.toModel(entity);
    }

    @Override
    public long getHardwareControllerIdFromSerialNumber(String serialNumber) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findBySerialNumber(serialNumber);
        return hardwareControllerEntity.getId();
    }

}
