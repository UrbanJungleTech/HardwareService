package urbanjungletech.hardwareservice.converter.hardware;

import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

public interface SpecificHardwareConverter<HardwareType extends Hardware,
        HardwareEntityType extends HardwareEntity> {
    HardwareType toModel(HardwareEntityType hardware);
    HardwareEntityType createEntity(HardwareType hardware);
    void fillEntity(HardwareEntityType hardwareEntity, HardwareType hardware);
}
