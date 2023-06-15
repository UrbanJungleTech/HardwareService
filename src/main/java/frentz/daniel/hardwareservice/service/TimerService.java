package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.Timer;

import java.util.List;

public interface TimerService {
    Timer getTimer(long id);
    List<Timer> getTimers();
}
