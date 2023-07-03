package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.HardwareStateType;

public interface HardwareStateAdditionService{
    HardwareState create(HardwareState hardwareState, HardwareStateType hardwareStateType);

    HardwareState update(long id, HardwareState hardwareState);
}
