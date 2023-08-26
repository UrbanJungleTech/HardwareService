package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.ScheduledHardware;

public interface ScheduledHardwareQueryService {

    void createScheduledHardware(ScheduledHardware scheduledHardware);

    ScheduledHardware getById(long scheduledHardwareId);
}
