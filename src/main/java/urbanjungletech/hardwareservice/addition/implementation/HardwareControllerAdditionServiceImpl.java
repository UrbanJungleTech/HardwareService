package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.event.hardwarecontroller.HardwareControllerEventPublisher;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;
import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HardwareControllerAdditionServiceImpl implements HardwareControllerAdditionService {

    private final HardwareControllerDAO hardwareControllerDAO;
    private final HardwareAdditionService hardwareAdditionService;
    private final SensorAdditionService sensorAdditionService;
    private final HardwareControllerConverter hardwareControllerConverter;
    private final ObjectLoggerService objectLoggerService;
    private final ControllerConfigurationService controllerConfigurationService;
    private final HardwareControllerEventPublisher hardwareControllerEventPublisher;
    private final CredentialsAdditionService credentialsAdditionService;


    public HardwareControllerAdditionServiceImpl(HardwareControllerDAO hardwareControllerDAO,
                                                 HardwareAdditionService hardwareAdditionService,
                                                 SensorAdditionService sensorAdditionService,
                                                 HardwareControllerConverter hardwareControllerConverter,
                                                 ObjectLoggerService objectLoggerService,
                                                 ControllerConfigurationService controllerConfigurationService,
                                                 HardwareControllerEventPublisher hardwareControllerEventPublisher,
                                                 CredentialsAdditionService credentialsAdditionService){
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareAdditionService = hardwareAdditionService;
        this.sensorAdditionService = sensorAdditionService;
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.objectLoggerService = objectLoggerService;
        this.controllerConfigurationService = controllerConfigurationService;
        this.hardwareControllerEventPublisher = hardwareControllerEventPublisher;
        this.credentialsAdditionService = credentialsAdditionService;
    }

    @Transactional
    @Override
    public HardwareController create(HardwareController hardwareController) {
        this.objectLoggerService.logInfo("Adding new hardware controller", hardwareController);
        if(hardwareController.getCredentials() != null){
            Credentials credentials = this.credentialsAdditionService.create(hardwareController.getCredentials());
            hardwareController.setCredentials(credentials);
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
        this.hardwareControllerEventPublisher.publishHardwareControllerCreateEvent(hardwareControllerId);
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
