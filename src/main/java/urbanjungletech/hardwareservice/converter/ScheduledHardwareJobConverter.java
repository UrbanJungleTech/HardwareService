package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.model.ScheduledHardware;

public interface ScheduledHardwareJobConverter {
    ScheduledHardware toScheduledHardware(Timer timer, ONOFF onoff);

    ScheduledHardware toScheduledHardware(ScheduledHardwareEntity scheduledHardwareEntity);

    void fillEntity(ScheduledHardware scheduledHardware, ScheduledHardwareEntity scheduledHardwareEntity);
}
