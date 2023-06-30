package frentz.daniel.hardwareservice.schedule.regulator;

import frentz.daniel.hardwareservice.model.Regulator;

public interface RegulatorScheduleService {
    void start(Regulator regulator);

    void stop(long regulatorId);
}
