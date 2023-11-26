package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;

import java.util.List;

public interface HardwareControllerGroupDAO {
    HardwareControllerGroupEntity createHardwareControllerGroup(HardwareControllerGroup hardwareControllerGroup);
    HardwareControllerGroupEntity updateHardwareControllerGroup(HardwareControllerGroup hardwareControllerGroup);
    HardwareControllerGroupEntity findById(long hardwareControllerGroupId);
    List<HardwareControllerGroupEntity> getAllHardwareControllers();
    void delete(long hardwareControllerGroupId);
}
