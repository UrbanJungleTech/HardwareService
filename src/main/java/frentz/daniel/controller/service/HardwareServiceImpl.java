package frentz.daniel.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import frentz.daniel.controller.converter.HardwareConverter;
import frentz.daniel.controller.converter.HardwareStateConverter;
import frentz.daniel.controller.converter.TimerConverter;
import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controller.entity.HardwareEntity;
import frentz.daniel.controller.entity.HardwareStateEntity;
import frentz.daniel.controller.entity.TimerEntity;
import frentz.daniel.controller.repository.HardwareRepository;
import frentz.daniel.controller.repository.TimerRepository;
import frentz.daniel.controllerclient.model.Hardware;
import frentz.daniel.controllerclient.model.HardwareState;
import frentz.daniel.controllerclient.model.ONOFF;
import frentz.daniel.controllerclient.model.Timer;
import frentz.daniel.model.CronJob;
import frentz.daniel.service.CronClient;
import org.springframework.stereotype.Service;

@Service
public class HardwareServiceImpl implements HardwareService{

    private HardwareRepository hardwareRepository;
    private HardwareConverter hardwareConverter;
    private HardwareStateConverter hardwareStateConverter;
    private TimerRepository timerRepository;
    private TimerConverter timerConverter;
    private HardwareQueueService queueService;
    private CronClient cronClient;

    public HardwareServiceImpl(HardwareRepository hardwareRepository,
                               HardwareConverter hardwareConverter,
                               HardwareStateConverter hardwareStateConverter,
                               TimerRepository timerRepository,
                               TimerConverter timerConverter,
                               HardwareQueueService queueService,
                               CronClient cronClient){
        this.hardwareConverter = hardwareConverter;
        this.hardwareRepository = hardwareRepository;
        this.hardwareStateConverter = hardwareStateConverter;
        this.timerRepository = timerRepository;
        this.timerConverter = timerConverter;
        this.queueService = queueService;
        this.cronClient = cronClient;
    }

    @Override
    public void createHardware(Hardware hardware) {
        HardwareState hardwareState = new HardwareState(ONOFF.OFF, 0);
        hardware.setCurrentState(hardwareState);
        hardware.setDesiredState(hardwareState);
    }

    @Override
    public HardwareEntity createAndSaveHardware(Hardware hardware, HardwareControllerEntity hardwareControllerEntity) {
        this.createHardware(hardware);
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setPort(hardware.getPort());
        hardwareEntity.setName(hardware.getName());
        hardwareEntity.setHardwareCategory(hardware.getHardwareCategory());
        HardwareStateEntity desiredState = this.hardwareStateConverter.toEntity(hardware.getDesiredState());
        hardwareEntity.setDesiredState(desiredState);
        HardwareStateEntity currentState = this.hardwareStateConverter.toEntity(hardware.getDesiredState());
        hardwareEntity.setCurrentState(currentState);
        hardwareEntity.setHardwareController(hardwareControllerEntity);
        return this.hardwareRepository.save(hardwareEntity);
    }

    @Override
    public Hardware setDesiredState(HardwareEntity hardwareEntity, HardwareState hardwareState) {
        HardwareStateEntity hardwareStateEntity = this.hardwareStateConverter.toEntity(hardwareState);
        hardwareEntity.setDesiredState(hardwareStateEntity);
        this.hardwareRepository.save(hardwareEntity);
        return this.hardwareConverter.toModel(hardwareEntity);
    }

    @Override
    public Hardware setStateConfirmation(String hardwareSerialNumber, long port, HardwareState hardwareState) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findHardwareBySerialNumberAndPort(hardwareSerialNumber, port);
        HardwareStateEntity hardwareStateEntity = this.hardwareStateConverter.toEntity(hardwareState);
        hardwareEntity.setCurrentState(hardwareStateEntity);
        hardwareEntity.setDesiredState(hardwareStateEntity);
        this.hardwareRepository.save(hardwareEntity);
        return this.hardwareConverter.toModel(hardwareEntity);
    }

    @Override
    public Hardware getHardware(long hardwareId) {
        System.out.println(hardwareId);
        HardwareEntity hardwareEntity = this.hardwareRepository.findById(hardwareId).orElseGet(null);
        return this.hardwareConverter.toModel(hardwareEntity);
    }

    @Override
    public Hardware addTimer(long hardwareId, Timer timer) throws JsonProcessingException {

        HardwareEntity hardwareEntity = this.hardwareRepository.findById(hardwareId).orElseGet(null);

        CronJob onCronJob = new CronJob();
        onCronJob.setCronString(timer.getOnCronExpression());
        HardwareState onState = new HardwareState();
        onState.setState(ONOFF.ON);
        onState.setLevel(timer.getOnLevel());
        String onMessageString = this.queueService.createStateChangeMessage(hardwareEntity.getPort(),
                onState, hardwareEntity.getHardwareController().getSerialNumber());
        onCronJob = this.cronClient.createCronJob(onCronJob);
        onCronJob.setQueue(this.queueService.getHardwareStateChangeQueue(hardwareEntity.getHardwareController().getSerialNumber()));


        CronJob offCronJob = new CronJob();
        offCronJob.setCronString(timer.getOffCronExpression());
        HardwareState offState = new HardwareState();
        offState.setState(ONOFF.OFF);
        String offMessageString = this.queueService.createStateChangeMessage(hardwareEntity.getPort(),
                offState, hardwareEntity.getHardwareController().getSerialNumber());
        offCronJob = this.cronClient.createCronJob(offCronJob);
        offCronJob.setQueue(this.queueService.getHardwareStateChangeQueue(hardwareEntity.getHardwareController().getSerialNumber()));

        TimerEntity timerEntity = new TimerEntity();
        timerEntity.setOnCronId(onCronJob.getId());
        timerEntity.setOffCronId(offCronJob.getId());
        timerEntity.setHardware(hardwareEntity);

        hardwareEntity.getTimers().add(timerEntity);
        this.timerRepository.save(timerEntity);
        this.hardwareRepository.save(hardwareEntity);

        return this.hardwareConverter.toModel(hardwareEntity);
    }

    @Override
    public Hardware getHardwareByPortAndSerialNumber(long port, String hardwareControllerSerialNumber) {
        HardwareEntity result = this.hardwareRepository.findHardwareBySerialNumberAndPort(hardwareControllerSerialNumber, port);
        return this.hardwareConverter.toModel(result);
    }
}
