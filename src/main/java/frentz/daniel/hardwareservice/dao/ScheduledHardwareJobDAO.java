package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.client.model.ScheduledHardware;

import java.util.List;

public interface ScheduledHardwareJobDAO {
    ScheduledHardwareEntity addScheduledHardwareJob(ScheduledHardware cronJob);
    List<ScheduledHardwareEntity> getAll();
    void delete(long scheduledHardwareId);
}
