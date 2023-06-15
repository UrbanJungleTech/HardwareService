package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.repository.HardwareCronJobRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.repository.TimerRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.client.model.ONOFF;
import frentz.daniel.hardwareservice.client.model.Timer;
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

    public TimerDAOImpl(TimerRepository timerRepository,
                        HardwareCronJobRepository cronJobrepository,
                        HardwareRepository hardwareRepository,
                        ExceptionService exceptionService,
                        TimerConverter timerConverter,
                        HardwareStateConverter hardwareStateConverter){
        this.timerRepository = timerRepository;
        this.cronJobrepository = cronJobrepository;
        this.hardwareRepository = hardwareRepository;
        this.exceptionService = exceptionService;
        this.timerConverter = timerConverter;
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public TimerEntity addTimer(Timer timer) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findById(timer.getHardwareId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareEntity.class, timer.getHardwareId());
        });
        TimerEntity timerEntity = new TimerEntity();
        timerEntity = this.timerRepository.save(timerEntity);

        ScheduledHardwareEntity onCronJobEntity = new ScheduledHardwareEntity();
        onCronJobEntity.setCronString(timer.getOnCronString());
        HardwareState onState = new HardwareState();
        onState.setState(ONOFF.ON);
        onState.setLevel(timer.getOnLevel());
        HardwareStateEntity onHardwareStateEntity = this.hardwareStateConverter.toEntity(onState);
        onCronJobEntity.setHardwareState(onHardwareStateEntity);
        onCronJobEntity.setTimerEntity(timerEntity);
        onCronJobEntity = this.cronJobrepository.save(onCronJobEntity);

        ScheduledHardwareEntity offCronJobEntity = new ScheduledHardwareEntity();
        offCronJobEntity.setCronString(timer.getOffCronString());
        HardwareState offState = new HardwareState();
        offState.setState(ONOFF.OFF);
        offState.setLevel(0);
        HardwareStateEntity offHardwareStateEntity = this.hardwareStateConverter.toEntity(offState);
        offCronJobEntity.setHardwareState(offHardwareStateEntity);
        offCronJobEntity.setTimerEntity(timerEntity);
        offCronJobEntity = this.cronJobrepository.save(offCronJobEntity);

        timerEntity.setHardware(hardwareEntity);
        timerEntity.setOnCronJob(onCronJobEntity);
        timerEntity.setOffCronJob(offCronJobEntity);

        hardwareEntity.getTimers().add(timerEntity);
        timerEntity = this.timerRepository.save(timerEntity);
        this.hardwareRepository.save(hardwareEntity);

        return timerEntity;
    }

    @Override
    public TimerEntity updateTimer(Timer timer) {
        return null;
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
        TimerEntity timerEntity = this.timerRepository.findById(timerId).get();
        return timerEntity;
    }

}
