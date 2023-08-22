package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.HardwareControllerService;
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

    @Override
    public String getSerialNumber(long hardwareControllerId) {
        return this.hardwareControllerDAO.getSerialNumber(hardwareControllerId);
    }

    @Override
    public HardwareController getHardwareControllerBySerialNumber(String serialNumber) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerDAO.getBySerialNumber(serialNumber);
        return this.hardwareControllerConverter.toModel(hardwareControllerEntity);
    }
}
