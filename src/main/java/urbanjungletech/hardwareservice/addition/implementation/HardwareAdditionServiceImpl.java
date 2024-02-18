package urbanjungletech.hardwareservice.addition.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.hardware.HardwareConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.event.hardware.HardwareEventPublisher;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.HardwareStateType;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class HardwareAdditionServiceImpl implements HardwareAdditionService {

    private final HardwareDAO hardwareDAO;
    private final TimerAdditionService timerAdditionService;
    private final HardwareConverter hardwareConverter;
    private final Logger logger = LoggerFactory.getLogger(HardwareAdditionServiceImpl.class);
    private final ObjectLoggerService objectLoggerService;
    private final HardwareEventPublisher hardwareEventPublisher;
    private final HardwareStateAdditionService hardwareStateAdditionService;
    private final HardwareQueryService hardwareQueryService;
    private final Map<Class, SpecificAdditionService> specificHardwareControllerAdditionServiceMap;

    public HardwareAdditionServiceImpl(HardwareDAO hardwareDAO,
                                       TimerAdditionService timerAdditionService,
                                       HardwareConverter hardwareConverter,
                                       ObjectLoggerService objectLoggerService,
                                       HardwareEventPublisher hardwareEventPublisher,
                                       HardwareStateAdditionService hardwareStateAdditionService,
                                       HardwareQueryService hardwareQueryService,
                                       Map<Class, SpecificAdditionService> specificHardwareControllerAdditionServiceMap) {
        this.hardwareDAO = hardwareDAO;
        this.timerAdditionService = timerAdditionService;
        this.hardwareConverter = hardwareConverter;
        this.objectLoggerService = objectLoggerService;
        this.hardwareEventPublisher = hardwareEventPublisher;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
        this.specificHardwareControllerAdditionServiceMap = specificHardwareControllerAdditionServiceMap;
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
        Optional.ofNullable(this.specificHardwareControllerAdditionServiceMap.get(hardware.getClass()))
                .ifPresent((specificHardwareControllerAdditionService) -> specificHardwareControllerAdditionService.create(hardware));
        HardwareEntity result = this.hardwareDAO.createHardware(hardware);
        hardware.setId(result.getId());
        if (hardware.getDesiredState() == null) {
            HardwareState hardwareState = new HardwareState(hardware.getOffState(), 0);
            hardware.setDesiredState(hardwareState);
        }
        if(hardware.getDesiredState().getState() == null){
            hardware.getDesiredState().setState(hardware.getOffState());
        }
        hardware.getDesiredState().setHardwareId(result.getId());
        this.hardwareStateAdditionService.create(hardware.getDesiredState(), HardwareStateType.DESIRED);
        if (hardware.getCurrentState() == null) {
            HardwareState hardwareState = new HardwareState(hardware.getOffState(), 0);
            hardware.setCurrentState(hardwareState);
        }
        if(hardware.getCurrentState().getState() == null){
            hardware.getCurrentState().setState(hardware.getOffState());
        }
        hardware.getCurrentState().setHardwareId(result.getId());
        this.hardwareStateAdditionService.create(hardware.getCurrentState(), HardwareStateType.CURRENT);

        Optional.ofNullable(hardware.getTimers()).ifPresent(timers -> timers.forEach((Timer timer) -> {
                timer.setHardwareId(result.getId());
                this.timerAdditionService.create(timer);
        }));
        hardwareEventPublisher.publishCreateHardwareEvent(result.getId());
        return this.hardwareConverter.toModel(result);
    }

    @Transactional
    @Override
    public void delete(long hardwareId) {
        Hardware hardware = this.hardwareQueryService.getHardware(hardwareId);
        hardware.getTimers().forEach((Timer timer) -> {
            this.timerAdditionService.delete(timer.getId());
        });
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
    @Transactional
    public HardwareState updateCurrentState(long hardwareId, HardwareState hardwareState) {
        HardwareEntity hardware = this.hardwareDAO.getHardware(hardwareId);
        return this.hardwareStateAdditionService.update(hardware.getCurrentState().getId(), hardwareState);
    }

    @Override
    @Transactional
    public HardwareState updateDesiredState(long hardwareId, HardwareState hardwareState) {
        HardwareEntity hardware = this.hardwareDAO.getHardware(hardwareId);
        return this.hardwareStateAdditionService.update(hardware.getDesiredState().getId(), hardwareState);
    }
}
