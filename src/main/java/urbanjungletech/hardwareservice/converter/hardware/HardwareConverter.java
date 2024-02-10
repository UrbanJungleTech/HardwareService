package urbanjungletech.hardwareservice.converter.hardware;

import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.List;

public interface HardwareConverter {
    Hardware toModel(HardwareEntity hardwareEntity);
    List<Hardware> toModels(List<HardwareEntity> entities);
    void fillEntity(HardwareEntity hardwareEntity, Hardware hardware);
    HardwareEntity createEntity(Hardware hardware);
}
