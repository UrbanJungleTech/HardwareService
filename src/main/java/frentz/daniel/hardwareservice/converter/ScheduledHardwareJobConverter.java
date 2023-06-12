package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.client.model.ScheduledHardware;

import java.util.List;

public interface ScheduledHardwareJobConverter {
    ScheduledHardware toModel(ScheduledHardwareEntity scheduledHardwareEntity);
    List<ScheduledHardware> toModels(List<ScheduledHardwareEntity> scheduledHardwareJobEntities);
    void fillEntity(ScheduledHardwareEntity scheduledHardwareEntity, ScheduledHardware scheduledHardware);
}
