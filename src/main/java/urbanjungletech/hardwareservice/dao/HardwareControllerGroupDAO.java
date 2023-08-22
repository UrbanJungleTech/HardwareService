package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;

public interface HardwareControllerGroupDAO {
    HardwareControllerGroupEntity createHardwareControllerGroup(HardwareControllerGroup hardwareControllerGroup);
    HardwareControllerGroupEntity updateHardwareControllerGroup(HardwareControllerGroup hardwareControllerGroup);
    HardwareControllerGroupEntity findById(long hardwareControllerGroupId);
    void delete(long hardwareControllerGroupId);
}
