package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.dao.ScheduledHardwareJobDAO;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.schedule.service.ScheduledHardwareScheduleService;
import frentz.daniel.hardwareservice.client.model.ScheduledHardware;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledHardwareJobAdditionServiceImpl implements ScheduledHardwareJobAdditionService{

    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    private ScheduledHardwareJobDAO scheduledHardwareJobDAO;
    private ScheduledHardwareJobConverter scheduledHardwareJobConverter;

    public ScheduledHardwareJobAdditionServiceImpl(ScheduledHardwareScheduleService scheduledHardwareScheduleService,
                                                   ScheduledHardwareJobDAO scheduledHardwareJobDAO,
                                                   ScheduledHardwareJobConverter scheduledHardwareJobConverter){
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;
        this.scheduledHardwareJobDAO = scheduledHardwareJobDAO;
        this.scheduledHardwareJobConverter = scheduledHardwareJobConverter;
    }

    @Override
    public ScheduledHardware create(ScheduledHardware scheduledHardware) {
        ScheduledHardwareEntity result = this.scheduledHardwareJobDAO.addScheduledHardwareJob(scheduledHardware);
        this.scheduledHardwareScheduleService.start(result);
        return this.scheduledHardwareJobConverter.toModel(result);
    }

    @Override
    public void delete(long scheduledHardwareId) {
        this.scheduledHardwareJobDAO.delete(scheduledHardwareId);
    }

    @Override
    //TODO: Implement this method
    public ScheduledHardware update(long scheduledHardwareId, ScheduledHardware scheduledHardware) {
        return null;
    }

    @Override
    //TODO: Implement this method
    public List<ScheduledHardware> updateList(List<ScheduledHardware> models) {
        return null;
    }
}
