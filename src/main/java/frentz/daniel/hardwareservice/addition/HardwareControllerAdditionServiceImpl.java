package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.converter.HardwareControllerConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.exception.DuplicateSerialNumberException;
import frentz.daniel.hardwareservice.service.HardwareControllerSubscriptionService;
import frentz.daniel.hardwareservice.service.ObjectLoggerService;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.Sensor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareControllerAdditionServiceImpl implements HardwareControllerAdditionService{

    private HardwareControllerDAO hardwareControllerDAO;
    private HardwareAdditionService hardwareAdditionService;
    private SensorAdditionService sensorAdditionService;
    private HardwareControllerConverter hardwareControllerConverter;
    private ObjectLoggerService objectLoggerService;

    public HardwareControllerAdditionServiceImpl(HardwareControllerDAO hardwareControllerDAO,
                                                 HardwareAdditionService hardwareAdditionService,
                                                 SensorAdditionService sensorAdditionService,
                                                 HardwareControllerConverter hardwareControllerConverter,
                                                 ObjectLoggerService objectLoggerService){
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareAdditionService = hardwareAdditionService;
        this.sensorAdditionService = sensorAdditionService;
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.objectLoggerService = objectLoggerService;
    }

    @Transactional
    @Override
    public HardwareController create(HardwareController hardwareController) {
        this.objectLoggerService.logInfo("Adding new hardware controller", hardwareController);
        if(this.hardwareControllerDAO.exists(hardwareController.getSerialNumber())){
            throw new DuplicateSerialNumberException();
        }
        HardwareControllerEntity result = this.hardwareControllerDAO.createHardwareController(hardwareController);
        Long hardwareControllerId = result.getId();
        hardwareController.getHardware().forEach((Hardware hardware) -> {
            hardware.setHardwareControllerId(hardwareControllerId);
        });
        this.hardwareAdditionService.updateList(hardwareController.getHardware());
        hardwareController.getSensors().forEach((Sensor sensor) -> {
            sensor.setHardwareControllerId(hardwareControllerId);
        });
        this.sensorAdditionService.updateList(hardwareController.getSensors());
        result = hardwareControllerDAO.getHardwareController(hardwareControllerId);
        return this.hardwareControllerConverter.toModel(result);
    }

    @Override
    public void delete(long hardwareControllerId) {
        this.hardwareControllerDAO.delete(hardwareControllerId);
    }

    @Override
    public HardwareController update(long hardwareControllerId, HardwareController hardwareController) {
        hardwareController.setId(hardwareControllerId);
        HardwareControllerEntity result = this.hardwareControllerDAO.updateHardwareController(hardwareController);
        hardwareController.getHardware().forEach((Hardware hardware) -> {
            hardware.setHardwareControllerId(result.getId());
        });
        hardwareAdditionService.updateList(hardwareController.getHardware());
        hardwareController.getSensors().forEach((Sensor sensor) -> {
            sensor.setHardwareControllerId(hardwareController.getId());
        });
        sensorAdditionService.updateList(hardwareController.getSensors());
        return this.hardwareControllerConverter.toModel(result);
    }

    @Override
    public List<HardwareController> updateList(List<HardwareController> hardwareControllers) {
        List<HardwareController> results = new ArrayList<>();
        for(HardwareController hardwareController : hardwareControllers){
            if(hardwareController.getId() != null){
                HardwareController result = this.update(hardwareController.getId(), hardwareController);
                results.add(result);
            }
            else{
                HardwareController result = this.create(hardwareController);
                results.add(result);
            }
        }
        return results;
    }

    @Override
    public Hardware addHardware(long hardwareControllerId, Hardware hardware) {
        hardware.setHardwareControllerId(hardwareControllerId);
        return this.hardwareAdditionService.create(hardware);
    }

    @Override
    public Sensor addSensor(long hardwareControllerId, Sensor sensor) {
        sensor.setHardwareControllerId(hardwareControllerId);
        return this.sensorAdditionService.create(sensor);
    }

    @Override
    public void deleteAll() {
        this.hardwareControllerDAO.deleteAll();
    }
}
