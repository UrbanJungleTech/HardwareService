package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.dao.ScheduledHardwareDAO;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.service.ScheduledHardwareService;
import org.springframework.stereotype.Service;

@Service
public class ScheduledHardwareServiceImpl implements ScheduledHardwareService {

    private ScheduledHardwareDAO scheduledHardwareDAO;
    private ScheduledHardwareJobConverter scheduledHardwareConverter;

    public ScheduledHardwareServiceImpl(ScheduledHardwareDAO scheduledHardwareDAO,
                                        ScheduledHardwareJobConverter scheduledHardwareJobConverter){
        this.scheduledHardwareDAO = scheduledHardwareDAO;
    }
    @Override
    public void createScheduledHardware(ScheduledHardware scheduledHardware) {
        this.scheduledHardwareDAO.addScheduledHardwareJob(scheduledHardware);
    }

    @Override
    public ScheduledHardware getById(long scheduledHardwareId) {
        ScheduledHardwareEntity scheduledHardwareEntity = this.scheduledHardwareDAO.getById(scheduledHardwareId);
        ScheduledHardware result = this.scheduledHardwareConverter.toScheduledHardware(scheduledHardwareEntity);
        return result;
    }
}
