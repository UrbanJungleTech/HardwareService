package urbanjungletech.hardwareservice.addition.implementation;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.event.hardwarecontroller.HardwareControllerEventPublisher;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HardwareControllerAdditionServiceImpl implements HardwareControllerAdditionService {

    private final HardwareControllerDAO hardwareControllerDAO;
    private final HardwareAdditionService hardwareAdditionService;
    private final SensorAdditionService sensorAdditionService;
    private final HardwareControllerConverter hardwareControllerConverter;
    private final ObjectLoggerService objectLoggerService;
    private final HardwareControllerEventPublisher hardwareControllerEventPublisher;
    private final Map<Class, SpecificAdditionService> specificHardwareControllerAdditionServiceMap;
    private final HardwareControllerQueryService hardwareControllerQueryService;


    public HardwareControllerAdditionServiceImpl(HardwareControllerDAO hardwareControllerDAO,
                                                 HardwareAdditionService hardwareAdditionService,
                                                 SensorAdditionService sensorAdditionService,
                                                 HardwareControllerConverter hardwareControllerConverter,
                                                 ObjectLoggerService objectLoggerService,
                                                 HardwareControllerEventPublisher hardwareControllerEventPublisher,
                                                 Map<Class, SpecificAdditionService> specificHardwareControllerAdditionServiceMap,
                                                 HardwareControllerQueryService hardwareControllerQueryService){
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareAdditionService = hardwareAdditionService;
        this.sensorAdditionService = sensorAdditionService;
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.objectLoggerService = objectLoggerService;
        this.hardwareControllerEventPublisher = hardwareControllerEventPublisher;
        this.specificHardwareControllerAdditionServiceMap = specificHardwareControllerAdditionServiceMap;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }

    @Transactional
    @Override
    @Observed
    public HardwareController create(HardwareController hardwareController) {
        this.objectLoggerService.logInfo("Adding new hardware controller", hardwareController);
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
        SpecificAdditionService specificHardwareControllerAdditionService = this.specificHardwareControllerAdditionServiceMap.get(hardwareController.getClass());
        if(specificHardwareControllerAdditionService != null){
            hardwareController.setId(hardwareControllerId);
            specificHardwareControllerAdditionService.create(hardwareController);
            this.hardwareControllerDAO.updateHardwareController(hardwareController);
        }
        result = hardwareControllerDAO.getHardwareController(hardwareControllerId);
        hardwareController = this.hardwareControllerConverter.toModel(result);
        this.hardwareControllerEventPublisher.publishHardwareControllerCreateEvent(hardwareControllerId);
        return hardwareController;
    }

    @Override
    @Transactional
    public void delete(long hardwareControllerId) {
        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareController(hardwareControllerId);
        hardwareController.getHardware().forEach((Hardware hardware) -> {
            this.hardwareAdditionService.delete(hardware.getId());
        });
        hardwareController.getSensors().forEach((Sensor sensor) -> {
            this.sensorAdditionService.delete(sensor.getId());
        });
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
