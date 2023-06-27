package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.model.HardwareState;

public interface HardwareStateConverter {
    HardwareState toModel(HardwareStateEntity hardwareState);
    HardwareStateEntity toEntity(HardwareState hardwareState);
    void fillEntity(HardwareStateEntity hardwareStateEntity, HardwareState hardwareState);
}
