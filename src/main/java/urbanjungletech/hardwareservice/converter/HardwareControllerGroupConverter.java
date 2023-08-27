package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;

public interface HardwareControllerGroupConverter {
    HardwareControllerGroup toModel(HardwareControllerGroupEntity hardwareControllerGroupEntity);
    void fillEntity(HardwareControllerGroupEntity hardwareControllerGroupEntity, HardwareControllerGroup hardwareControllerGroup);
}
