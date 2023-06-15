package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.client.model.*;

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
}
