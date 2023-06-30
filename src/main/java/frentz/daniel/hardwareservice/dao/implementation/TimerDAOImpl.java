package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.repository.HardwareCronJobRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.repository.TimerRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.ONOFF;
import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.service.ScheduledHardwareService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimerDAOImpl implements TimerDAO {

    private TimerRepository timerRepository;
    private HardwareCronJobRepository cronJobrepository;
    private HardwareRepository hardwareRepository;
    private ExceptionService exceptionService;
    private TimerConverter timerConverter;
    private HardwareStateConverter hardwareStateConverter;
    private ScheduledHardwareService scheduledHardwareService;
    private ScheduledHardwareJobConverter scheduledHardwareJobConverter;

    public TimerDAOImpl(TimerRepository timerRepository,
                        HardwareCronJobRepository cronJobrepository,
                        HardwareRepository hardwareRepository,
                        ExceptionService exceptionService,
                        TimerConverter timerConverter,
                        HardwareStateConverter hardwareStateConverter,
                        ScheduledHardwareService scheduledHardwareService){
        this.timerRepository = timerRepository;
        this.cronJobrepository = cronJobrepository;
        this.hardwareRepository = hardwareRepository;
        this.exceptionService = exceptionService;
        this.timerConverter = timerConverter;
        this.hardwareStateConverter = hardwareStateConverter;
        this.scheduledHardwareService = scheduledHardwareService;
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
