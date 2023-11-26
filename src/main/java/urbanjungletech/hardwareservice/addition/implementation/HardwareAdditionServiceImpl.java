package urbanjungletech.hardwareservice.addition.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.builder.HardwareStateBuilder;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.event.hardware.HardwareEventPublisher;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.HardwareStateType;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;

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
    private final HardwareStateConverter hardwareStateConverter;
    private final HardwareEventPublisher hardwareEventPublisher;
    private final HardwareStateAdditionService hardwareStateAdditionService;

    public HardwareAdditionServiceImpl(HardwareDAO hardwareDAO,
                                       TimerAdditionService timerAdditionService,
                                       HardwareStateBuilder hardwareStateBuilder,
                                       HardwareConverter hardwareConverter,
                                       ObjectLoggerService objectLoggerService,
                                       HardwareStateConverter hardwareStateConverter,
                                       HardwareEventPublisher hardwareEventPublisher,
                                       HardwareStateAdditionService hardwareStateAdditionService) {
        this.hardwareDAO = hardwareDAO;
        this.timerAdditionService = timerAdditionService;
        this.hardwareStateBuilder = hardwareStateBuilder;
        this.hardwareConverter = hardwareConverter;
        this.objectLoggerService = objectLoggerService;
        this.hardwareStateConverter = hardwareStateConverter;
        this.hardwareEventPublisher = hardwareEventPublisher;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
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
        HardwareEntity result = this.hardwareDAO.createHardware(hardware);
        if (hardware.getDesiredState() == null) {
            HardwareState hardwareState = this.hardwareStateBuilder.getOffHardwareState();
            hardware.setDesiredState(hardwareState);
        }
        hardware.getDesiredState().setHardwareId(result.getId());
        this.hardwareStateAdditionService.create(hardware.getDesiredState(), HardwareStateType.DESIRED);
        if (hardware.getCurrentState() == null) {
            HardwareState hardwareState = this.hardwareStateBuilder.getOffHardwareState();
            hardware.setCurrentState(hardwareState);
        }
        hardware.getCurrentState().setHardwareId(result.getId());
        this.hardwareStateAdditionService.create(hardware.getCurrentState(), HardwareStateType.CURRENT);

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
        HardwareEntity result = this.hardwareDAO.updateHardware(hardware);
        if (hardware.getDesiredState() != null) {
            this.hardwareStateAdditionService.update(result.getDesiredState().getId(), hardware.getDesiredState());
        }
        if (hardware.getCurrentState() != null) {
            this.hardwareStateAdditionService.update(result.getCurrentState().getId(), hardware.getCurrentState());
        }
        Optional.ofNullable(hardware.getTimers()).ifPresent(timers -> timers.forEach((Timer timer) -> {
            timer.setHardwareId(result.getId());
        }));
        Optional.ofNullable(hardware.getTimers())
                .ifPresent((timers) -> this.timerAdditionService.updateList(timers));
        Hardware hardwareResult = this.hardwareConverter.toModel(result);
        return hardwareResult;
    }

    @Override
    @Transactional
    public Timer addTimer(long hardwareId, Timer timer) {
        timer.setHardwareId(hardwareId);
        return this.timerAdditionService.create(timer);
    }

    @Override
    public HardwareState updateCurrentState(long hardwareId, HardwareState hardwareState) {
        HardwareEntity hardware = this.hardwareDAO.getHardware(hardwareId);
        return this.hardwareStateAdditionService.update(hardware.getCurrentState().getId(), hardwareState);
    }

    @Override
    public HardwareState updateDesiredState(long hardwareId, HardwareState hardwareState) {
        HardwareEntity hardware = this.hardwareDAO.getHardware(hardwareId);
        return this.hardwareStateAdditionService.update(hardware.getDesiredState().getId(), hardwareState);
    }
}
