package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.converter.HardwareControllerConverter;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.SensorConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareControllerServiceImpl implements HardwareControllerService {
    private HardwareDAO hardwareDAO;
    private HardwareControllerDAO hardwareControllerDAO;
    private HardwareControllerConverter hardwareControllerConverter;
    private SensorDAO sensorDAO;
    private SensorConverter sensorConverter;
    private HardwareConverter hardwareConverter;

    public HardwareControllerServiceImpl(HardwareControllerDAO hardwareControllerDAO,
                                         HardwareControllerConverter hardwareControllerConverter,
                                         HardwareDAO hardwareDAO,
                                         SensorDAO sensorDAO,
                                         SensorConverter sensorConverter,
                                         HardwareConverter hardwareConverter){
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.hardwareDAO = hardwareDAO;
        this.sensorDAO = sensorDAO;
        this.sensorConverter = sensorConverter;
        this.hardwareConverter = hardwareConverter;
    }

    @Override
    public HardwareController getHardwareController(long hardwareControllerId) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerDAO.getHardwareController(hardwareControllerId);
        return this.hardwareControllerConverter.toModel(hardwareControllerEntity);
    }

    @Override
    public List<HardwareController> getAllHardwareControllers() {
        List<HardwareControllerEntity> hardwareControllerEntities = this.hardwareControllerDAO.getAllHardwareControllers();
        return this.hardwareControllerConverter.toModels(hardwareControllerEntities);
    }

    @Override
    public List<Hardware> getHardware(long HardwareControllerId) {
        List<HardwareEntity> hardwareEntities = this.hardwareDAO.getHardwareByHardwareControllerId(HardwareControllerId);
        List<Hardware> result = this.hardwareConverter.toModels(hardwareEntities);
        return result;
    }

    @Override
    public List<Sensor> getSensors(long hardwareControllerId) {
        List<SensorEntity> sensors = this.sensorDAO.getSensorsByHardwareControllerId(hardwareControllerId);
        List<Sensor> result = this.sensorConverter.toModels(sensors);
        return result;
    }
}
