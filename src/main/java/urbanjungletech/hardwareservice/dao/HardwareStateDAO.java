package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.HardwareStateType;

public interface HardwareStateDAO {
    HardwareStateEntity createHardwareState(HardwareState hardwareState, HardwareStateType hardwareStateType);
    HardwareStateEntity updateHardwareState(HardwareState hardwareState);
    HardwareStateEntity findById(long hardwareStateId);
}
