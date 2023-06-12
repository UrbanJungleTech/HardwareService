package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.Timer;

public interface HardwareAdditionService extends AdditionService<Hardware> {
    Timer addTimer(long hardwareId, Timer timer);
}
