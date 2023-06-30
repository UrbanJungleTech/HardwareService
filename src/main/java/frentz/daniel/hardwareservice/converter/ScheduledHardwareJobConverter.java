package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.model.ONOFF;
import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.ScheduledHardware;

import java.util.List;

public interface ScheduledHardwareJobConverter {
    ScheduledHardware toScheduledHardware(Timer timer, ONOFF onoff);

    ScheduledHardware toScheduledHardware(ScheduledHardwareEntity scheduledHardwareEntity);

    void fillEntity(ScheduledHardware scheduledHardware, ScheduledHardwareEntity scheduledHardwareEntity);
}
