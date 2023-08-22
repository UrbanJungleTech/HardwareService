package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.HardwareStateType;

public interface HardwareStateAdditionService{
    HardwareState create(HardwareState hardwareState, HardwareStateType hardwareStateType);

    HardwareState update(long id, HardwareState hardwareState);
}
