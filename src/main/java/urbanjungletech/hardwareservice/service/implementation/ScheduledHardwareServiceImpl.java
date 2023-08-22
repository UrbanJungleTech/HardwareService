package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.converter.ScheduledHardwareJobConverter;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.ScheduledHardwareService;
import org.springframework.stereotype.Service;

@Service
public class ScheduledHardwareServiceImpl implements ScheduledHardwareService {

    private ScheduledHardwareDAO scheduledHardwareDAO;
    private ScheduledHardwareJobConverter scheduledHardwareConverter;

    public ScheduledHardwareServiceImpl(ScheduledHardwareDAO scheduledHardwareDAO,
                                        ScheduledHardwareJobConverter scheduledHardwareJobConverter){
        this.scheduledHardwareDAO = scheduledHardwareDAO;
        this.scheduledHardwareConverter = scheduledHardwareJobConverter;
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
