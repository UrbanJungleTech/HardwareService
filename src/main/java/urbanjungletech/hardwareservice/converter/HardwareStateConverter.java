package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;

public interface HardwareStateConverter {
    HardwareState toModel(HardwareStateEntity hardwareState);
    HardwareStateEntity toEntity(HardwareState hardwareState);
    void fillEntity(HardwareStateEntity hardwareStateEntity, HardwareState hardwareState);
}
