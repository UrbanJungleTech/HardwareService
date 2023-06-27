package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.model.HardwareController;

import java.util.List;

public interface HardwareControllerConverter {
    HardwareController toModel(HardwareControllerEntity hardwareControllerEntity);
    List<HardwareController> toModels(List<HardwareControllerEntity> hardwareControllerEntities);
    void fillEntity(HardwareControllerEntity hardwareControllerEntity, HardwareController hardwareController);
}
