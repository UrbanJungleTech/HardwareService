package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.model.ScheduledHardware;

public interface ScheduledHardwareService {

    void createScheduledHardware(ScheduledHardware scheduledHardware);

    ScheduledHardware getById(long scheduledHardwareId);
}
