package frentz.daniel.hardwareservice.schedule.service;

import frentz.daniel.hardwareservice.client.model.Regulator;

public interface RegulatorScheduleService {
    void start(Regulator regulator);
}