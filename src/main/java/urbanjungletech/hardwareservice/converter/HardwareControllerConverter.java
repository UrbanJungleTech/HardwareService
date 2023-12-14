package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

import java.util.List;

public interface HardwareControllerConverter {
    HardwareController toModel(HardwareControllerEntity hardwareControllerEntity);
    List<HardwareController> toModels(List<HardwareControllerEntity> hardwareControllerEntities);
    void fillEntity(HardwareControllerEntity hardwareControllerEntity, HardwareController hardwareController);
    HardwareControllerEntity createEntity(HardwareController hardwareController);
}
