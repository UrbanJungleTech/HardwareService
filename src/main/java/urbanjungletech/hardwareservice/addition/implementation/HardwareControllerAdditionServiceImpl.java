package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.exception.exception.DuplicateSerialNumberException;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationService;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareControllerAdditionServiceImpl implements HardwareControllerAdditionService {

    private HardwareControllerDAO hardwareControllerDAO;
    private HardwareAdditionService hardwareAdditionService;
    private SensorAdditionService sensorAdditionService;
    private HardwareControllerConverter hardwareControllerConverter;
    private ObjectLoggerService objectLoggerService;
    private ControllerConfigurationService controllerConfigurationService;



    public HardwareControllerAdditionServiceImpl(HardwareControllerDAO hardwareControllerDAO,
                                                 HardwareAdditionService hardwareAdditionService,
                                                 SensorAdditionService sensorAdditionService,
                                                 HardwareControllerConverter hardwareControllerConverter,
                                                 ObjectLoggerService objectLoggerService,
                                                 ControllerConfigurationService controllerConfigurationService){
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareAdditionService = hardwareAdditionService;
        this.sensorAdditionService = sensorAdditionService;
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.objectLoggerService = objectLoggerService;
        this.controllerConfigurationService = controllerConfigurationService;
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
        hardwareController = this.hardwareControllerConverter.toModel(result);
        this.controllerConfigurationService.configureController(hardwareController);
        return hardwareController;
    }

    @Override
    @Transactional
    public void delete(long hardwareControllerId) {
        this.hardwareControllerDAO.delete(hardwareControllerId);
    }

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public Hardware addHardware(long hardwareControllerId, Hardware hardware) {
        hardware.setHardwareControllerId(hardwareControllerId);
        return this.hardwareAdditionService.create(hardware);
    }

    @Override
    @Transactional
    public Sensor addSensor(long hardwareControllerId, Sensor sensor) {
        sensor.setHardwareControllerId(hardwareControllerId);
        return this.sensorAdditionService.create(sensor);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.hardwareControllerDAO.deleteAll();
    }
}
