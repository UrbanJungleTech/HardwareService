package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.client.model.Hardware;

import java.util.List;

public interface HardwareConverter {
    Hardware toModel(HardwareEntity hardwareEntity);
    List<Hardware> toModels(List<HardwareEntity> entities);
    void fillEntity(HardwareEntity hardwareEntity, Hardware hardware);
}
