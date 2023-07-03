package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.model.HardwareState;

public interface HardwareStateService {
    HardwareState getHardwareStateById(long hardwareStateId);
}
