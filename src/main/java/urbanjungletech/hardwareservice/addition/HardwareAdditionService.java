package urbanjungletech.hardwareservice.addition;


import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

public interface HardwareAdditionService extends AdditionService<Hardware> {
    Timer addTimer(long hardwareId, Timer timer);

    HardwareState updateCurrentState(long hardwareId, HardwareState hardwareState);

    HardwareState updateDesiredState(long hardwareId, HardwareState hardwareState);
}
