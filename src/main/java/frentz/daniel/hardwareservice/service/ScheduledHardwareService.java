package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.model.ONOFF;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.model.Timer;

public interface ScheduledHardwareService {

    void createScheduledHardware(ScheduledHardware scheduledHardware);

    ScheduledHardware getById(long scheduledHardwareId);
}
