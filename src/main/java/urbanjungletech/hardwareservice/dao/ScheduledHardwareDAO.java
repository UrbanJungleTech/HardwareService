package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.model.ScheduledHardware;

import java.util.List;

public interface ScheduledHardwareDAO {
    ScheduledHardwareEntity addScheduledHardwareJob(ScheduledHardware cronJob);
    List<ScheduledHardwareEntity> getAll();

    ScheduledHardwareEntity getById(long scheduledHardwareId);
}
