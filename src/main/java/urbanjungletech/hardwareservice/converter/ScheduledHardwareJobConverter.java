package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.model.Timer;

public interface ScheduledHardwareJobConverter {
    ScheduledHardware toScheduledHardware(Timer timer, ONOFF onoff);

    ScheduledHardware toScheduledHardware(ScheduledHardwareEntity scheduledHardwareEntity);

    void fillEntity(ScheduledHardware scheduledHardware, ScheduledHardwareEntity scheduledHardwareEntity);
}
