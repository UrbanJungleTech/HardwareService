package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.HardwareStateType;

public interface HardwareStateDAO {
    HardwareStateEntity createHardwareState(HardwareState hardwareState, HardwareStateType hardwareStateType);
    HardwareStateEntity updateHardwareState(HardwareState hardwareState);
    HardwareStateEntity findById(long hardwareStateId);
}
