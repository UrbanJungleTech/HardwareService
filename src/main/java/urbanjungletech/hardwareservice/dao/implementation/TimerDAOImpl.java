package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.ScheduledHardwareJobConverter;
import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.dao.TimerDAO;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.repository.HardwareCronJobRepository;
import urbanjungletech.hardwareservice.repository.HardwareRepository;
import urbanjungletech.hardwareservice.repository.TimerRepository;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;

import java.util.List;

@Service
public class TimerDAOImpl implements TimerDAO {

    private final TimerRepository timerRepository;
    private final HardwareCronJobRepository cronJobrepository;
    private final HardwareRepository hardwareRepository;
    private final ExceptionService exceptionService;
    private final TimerConverter timerConverter;

    public TimerDAOImpl(TimerRepository timerRepository,
                        HardwareCronJobRepository cronJobrepository,
                        HardwareRepository hardwareRepository,
                        ExceptionService exceptionService,
                        TimerConverter timerConverter){
        this.timerRepository = timerRepository;
        this.cronJobrepository = cronJobrepository;
        this.hardwareRepository = hardwareRepository;
        this.exceptionService = exceptionService;
        this.timerConverter = timerConverter;
    }

    @Override
    public TimerEntity addTimer(Timer timer) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findById(timer.getHardwareId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareEntity.class, timer.getHardwareId());
        });
        TimerEntity timerEntity = new TimerEntity();
        timerEntity = this.timerRepository.save(timerEntity);
        timerEntity.setHardware(hardwareEntity);

        hardwareEntity.getTimers().add(timerEntity);
        timerEntity = this.timerRepository.save(timerEntity);
        this.hardwareRepository.save(hardwareEntity);

        return timerEntity;
    }

    @Override
    public TimerEntity updateTimer(Timer timer) {
        TimerEntity timerEntity = this.getTimer(timer.getId());
        this.timerConverter.fillEntity(timerEntity, timer);
        timerEntity = this.timerRepository.save(timerEntity);
        return timerEntity;
    }

    @Override
    public void delete(long timerId) {
        TimerEntity timerEntity = this.getTimer(timerId);
        timerEntity.getHardware().getTimers().removeIf((TimerEntity timer) -> {
            return timer.getId() == timerId;
        });
        this.timerRepository.deleteById(timerId);
    }

    @Override
    public List<TimerEntity> getTimers() {
        return this.timerRepository.findAll();
    }

    @Override
    public TimerEntity getTimer(long timerId) {
        TimerEntity timerEntity = this.timerRepository.findById(timerId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(TimerEntity.class, timerId);
        });
        return timerEntity;
    }

}
