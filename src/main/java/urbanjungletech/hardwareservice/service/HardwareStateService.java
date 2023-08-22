package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.model.HardwareState;

public interface HardwareStateService {
    HardwareState getHardwareStateById(long hardwareStateId);
}
