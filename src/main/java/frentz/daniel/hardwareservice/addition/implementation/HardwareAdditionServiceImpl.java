package frentz.daniel.hardwareservice.addition.implementation;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.addition.TimerAdditionService;
import frentz.daniel.hardwareservice.builder.HardwareStateBuilder;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.event.hardware.HardwareEventPublisher;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.service.ObjectLoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class HardwareAdditionServiceImpl implements HardwareAdditionService {

    private final HardwareDAO hardwareDAO;
    private final TimerAdditionService timerAdditionService;
    private final HardwareStateBuilder hardwareStateBuilder;
    private final HardwareConverter hardwareConverter;
    private final Logger logger = LoggerFactory.getLogger(HardwareAdditionServiceImpl.class);
    private final ObjectLoggerService objectLoggerService;
    private HardwareStateConverter hardwareStateConverter;
    private HardwareEventPublisher hardwareEventPublisher;


    public HardwareAdditionServiceImpl(HardwareDAO hardwareDAO,
                                       TimerAdditionService timerAdditionService,
                                       HardwareStateBuilder hardwareStateBuilder,
                                       HardwareConverter hardwareConverter,
                                       ObjectLoggerService objectLoggerService,
                                       HardwareStateConverter hardwareStateConverter,
                                       HardwareEventPublisher hardwareEventPublisher) {
        this.hardwareDAO = hardwareDAO;
        this.timerAdditionService = timerAdditionService;
        this.hardwareStateBuilder = hardwareStateBuilder;
        this.hardwareConverter = hardwareConverter;
        this.objectLoggerService = objectLoggerService;
        this.hardwareStateConverter = hardwareStateConverter;
        this.hardwareEventPublisher = hardwareEventPublisher;
    }

    @Transactional
    @Override
    public List<Hardware> updateList(List<Hardware> hardwares) {
        List<Hardware> results = new ArrayList<>();
        for (Hardware hardware : hardwares) {
            Hardware result;
            if (hardware.getId() != null) {
                result = this.update(hardware.getId(), hardware);
            } else {
                result = this.create(hardware);
            }
            results.add(result);
        }
        return results;
    }

    @Override
    @Transactional
    public Hardware create(Hardware hardware) {
        this.objectLoggerService.logInfo("Creating hardware", hardware);
        if (hardware.getDesiredState() == null) {
            HardwareState hardwareState = this.hardwareStateBuilder.getOffHardwareState();
            hardware.setDesiredState(hardwareState);
        }
        if (hardware.getCurrentState() == null) {
            HardwareState hardwareState = this.hardwareStateBuilder.getOffHardwareState();
            hardware.setCurrentState(hardwareState);
        }
        HardwareEntity result = this.hardwareDAO.createHardware(hardware);
        if (hardware.getTimers() != null) {
            hardware.getTimers().forEach((timer) -> {
                timer.setHardwareId(result.getId());
                this.timerAdditionService.create(timer);
            });
        }
        hardwareEventPublisher.publishCreateHardwareEvent(result.getId());
        return this.hardwareConverter.toModel(result);
    }

    @Transactional
    @Override
    public void delete(long hardwareId) {
        this.hardwareEventPublisher.publishDeleteHardwareEvent(hardwareId);
        this.hardwareDAO.delete(hardwareId);
    }

    @Transactional
    @Override
    public Hardware update(long hardwareId, Hardware hardware) {
        hardware.setId(hardwareId);
        HardwareEntity current = this.hardwareDAO.getHardware(hardware.getId());
        if (hardware.getDesiredState() == null) {
            hardware.setDesiredState(hardwareStateConverter.toModel(current.getDesiredState()));
        }
        if (hardware.getCurrentState() == null) {
            hardware.setCurrentState(hardwareStateConverter.toModel(current.getCurrentState()));
        }
        boolean updateHardwareState = hardware.getDesiredState().getState() != current.getDesiredState().getState();
        HardwareEntity result = this.hardwareDAO.updateHardware(hardware);
        Optional.ofNullable(hardware.getTimers()).ifPresent(timers -> timers.forEach((Timer timer) -> {
            timer.setHardwareId(result.getId());
        }));
        Optional.ofNullable(hardware.getTimers())
                .ifPresent((timers) -> this.timerAdditionService.updateList(timers));
        if (updateHardwareState) {
            this.hardwareEventPublisher.publishUpdateHardwareStateEvent(result.getId());
        }
        Hardware hardwareResult = this.hardwareConverter.toModel(result);
        return hardwareResult;
    }

    @Override
    @Transactional
    public Timer addTimer(long hardwareId, Timer timer) {
        timer.setHardwareId(hardwareId);
        return this.timerAdditionService.create(timer);
    }
}
