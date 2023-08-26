package urbanjungletech.hardwareservice.dao.implementation;

import urbanjungletech.hardwareservice.converter.ScheduledHardwareJobConverter;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.repository.HardwareCronJobRepository;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.repository.TimerRepository;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledHardwareDAOImpl implements ScheduledHardwareDAO {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareDAOImpl.class);

    private HardwareCronJobRepository hardwareCronJobRepository;
    private ScheduledHardwareJobConverter scheduledHardwareJobConverter;
    private ExceptionService exceptionService;
    private TimerRepository timerRepository;
    public ScheduledHardwareDAOImpl(HardwareCronJobRepository cronJobrepository,
                                    ScheduledHardwareJobConverter scheduledHardwareJobConverter,
                                    TimerRepository timerRepository,
                                    ExceptionService exceptionService){
        this.hardwareCronJobRepository = cronJobrepository;
        this.scheduledHardwareJobConverter = scheduledHardwareJobConverter;
        this.exceptionService = exceptionService;
        this.timerRepository = timerRepository;
    }

    @Override
    public ScheduledHardwareEntity addScheduledHardwareJob(ScheduledHardware scheduledHardware) {
        ScheduledHardwareEntity scheduledHardwareEntity = new ScheduledHardwareEntity();
        this.scheduledHardwareJobConverter.fillEntity(scheduledHardware, scheduledHardwareEntity);
        TimerEntity timerEntity = this.timerRepository.findById(scheduledHardware.getTimerId())
                .orElseThrow(() -> this.exceptionService.createNotFoundException(TimerEntity.class, scheduledHardware.getTimerId()));
        scheduledHardwareEntity.setTimerEntity(timerEntity);
        scheduledHardwareEntity = this.hardwareCronJobRepository.save(scheduledHardwareEntity);
        if(scheduledHardware.getOnoff() == ONOFF.ON){
            timerEntity.setOnCronJob(scheduledHardwareEntity);
        }
        else{
            timerEntity.setOffCronJob(scheduledHardwareEntity);
        }
        this.timerRepository.save(timerEntity);
        return scheduledHardwareEntity;
    }

    @Override
    public List<ScheduledHardwareEntity> getAll() {
        List<ScheduledHardwareEntity> jobs = this.hardwareCronJobRepository.findAll();
        return jobs;
    }

    @Override
    public ScheduledHardwareEntity getById(long scheduledHardwareId) {
        return this.hardwareCronJobRepository.findById(scheduledHardwareId)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(ScheduledHardwareEntity.class, scheduledHardwareId));
    }
}
