package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

import java.util.List;

public interface HardwareControllerDAO {
    HardwareControllerEntity createHardwareController(HardwareController hardwareController);
    List<HardwareControllerEntity> getAllHardwareControllers();
    HardwareControllerEntity getHardwareController(long hardwareControllerId);
    HardwareControllerEntity getBySerialNumber(String serialNumber);
    boolean exists(String serialNumber);
    HardwareControllerEntity updateHardwareController(HardwareController hardwareController);
    String getHardwareControllerSerialNumber(long hardwareControllerId);
    void delete(Long id);
    String getSerialNumber(long hardwareControllerId);

    void deleteAll();
    String getControllerType(String serialNumber);
}
