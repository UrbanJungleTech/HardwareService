package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.converter.ScheduledHardwareJobConverter;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;
import org.springframework.stereotype.Service;

@Service
public class ScheduledHardwareQueryServiceImpl implements ScheduledHardwareQueryService {

    private ScheduledHardwareDAO scheduledHardwareDAO;
    private ScheduledHardwareJobConverter scheduledHardwareConverter;

    public ScheduledHardwareQueryServiceImpl(ScheduledHardwareDAO scheduledHardwareDAO,
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
