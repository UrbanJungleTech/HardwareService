package urbanjungletech.hardwareservice.converter.hardwarecontroller;

import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

public interface SpecificHardwareControllerConverter<HardwareControllerType extends HardwareController,
        HardwareControllerEntityType extends HardwareControllerEntity>{
    void fillEntity(HardwareControllerEntityType hardwareControllerEntity, HardwareControllerType hardwareController);

    HardwareControllerType toModel(HardwareControllerEntityType hardwareControllerEntity);
    HardwareControllerEntityType createEntity(HardwareControllerType hardwareController);
}
