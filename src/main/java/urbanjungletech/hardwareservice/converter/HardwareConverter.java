package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.model.Hardware;

import java.util.List;

public interface HardwareConverter {
    Hardware toModel(HardwareEntity hardwareEntity);
    List<Hardware> toModels(List<HardwareEntity> entities);
    void fillEntity(HardwareEntity hardwareEntity, Hardware hardware);
}
