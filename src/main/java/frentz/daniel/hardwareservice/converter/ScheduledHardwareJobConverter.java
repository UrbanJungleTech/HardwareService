package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.ScheduledHardware;

import java.util.List;

public interface ScheduledHardwareJobConverter {
    ScheduledHardware toModel(ScheduledHardwareEntity scheduledHardwareEntity);
    List<ScheduledHardware> toModels(List<ScheduledHardwareEntity> scheduledHardwareJobEntities);
    void fillOnEntity(Timer timer, ScheduledHardwareEntity scheduledHardwareEntity);
    void fillOffEntity(Timer timer, ScheduledHardwareEntity scheduledHardwareEntity);
}
