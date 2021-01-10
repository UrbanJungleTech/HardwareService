package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.HardwareStateEntity;
import frentz.daniel.controllerclient.model.HardwareState;

public interface HardwareStateConverter {
    HardwareState toModel(HardwareStateEntity hardwareState);
    HardwareStateEntity toEntity(HardwareState hardwareState);
}
