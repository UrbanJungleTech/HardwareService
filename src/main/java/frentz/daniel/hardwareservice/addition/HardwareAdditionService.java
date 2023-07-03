package frentz.daniel.hardwareservice.addition;


import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.Timer;

public interface HardwareAdditionService extends AdditionService<Hardware> {
    Timer addTimer(long hardwareId, Timer timer);

    HardwareState updateCurrentState(long hardwareId, HardwareState hardwareState);

    HardwareState updateDesiredState(long hardwareId, HardwareState hardwareState);
}
