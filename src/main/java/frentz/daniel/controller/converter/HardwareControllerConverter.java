package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controllerclient.model.HardwareController;

import java.util.List;

public interface HardwareControllerConverter {
    HardwareController toModel(HardwareControllerEntity hardwareControllerEntity);
    List<HardwareController> toModels(List<HardwareControllerEntity> hardwareControllerEntities);
}
