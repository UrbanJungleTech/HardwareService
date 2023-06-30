package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.ScheduledHardware;

import java.util.List;

public interface ScheduledHardwareDAO {
    ScheduledHardwareEntity addScheduledHardwareJob(ScheduledHardware cronJob);
    List<ScheduledHardwareEntity> getAll();

    ScheduledHardwareEntity getById(long scheduledHardwareId);
}
