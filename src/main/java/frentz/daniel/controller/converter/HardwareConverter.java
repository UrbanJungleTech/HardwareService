package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.HardwareEntity;
import frentz.daniel.controllerclient.model.Hardware;

import java.util.List;

public interface HardwareConverter {
    Hardware toModel(HardwareEntity hardwareEntity);

    List<Hardware> toModels(List<HardwareEntity> entities);
}
