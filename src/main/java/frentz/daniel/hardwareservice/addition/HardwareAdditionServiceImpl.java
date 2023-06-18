package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.builder.HardwareStateBuilder;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.service.ObjectLoggerService;
import frentz.daniel.hardwareservice.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class HardwareAdditionServiceImpl implements HardwareAdditionService{

    private final HardwareDAO hardwareDAO;
    private final TimerAdditionService timerAdditionService;
    private final HardwareQueueService hardwareQueueService;
    private final HardwareStateBuilder hardwareStateBuilder;
    private final HardwareConverter hardwareConverter;
    private final Logger logger = LoggerFactory.getLogger(HardwareAdditionServiceImpl.class);
    private final ObjectLoggerService objectLoggerService;
    private HardwareStateConverter hardwareStateConverter;

    public HardwareAdditionServiceImpl(HardwareDAO hardwareDAO,
                                       TimerAdditionService timerAdditionService,
                                       HardwareQueueService hardwareQueueService,
                                       HardwareStateBuilder hardwareStateBuilder,
                                       HardwareConverter hardwareConverter,
                                       ObjectLoggerService objectLoggerService,
                                       HardwareStateConverter hardwareStateConverter){
        this.hardwareDAO = hardwareDAO;
        this.timerAdditionService = timerAdditionService;
        this.hardwareQueueService = hardwareQueueService;
        this.hardwareStateBuilder = hardwareStateBuilder;
        this.hardwareConverter = hardwareConverter;
        this.objectLoggerService = objectLoggerService;
        this.hardwareStateConverter = hardwareStateConverter;
    }

    public List<Hardware> updateList(List<Hardware> hardwares){
        List<Hardware> results = new ArrayList<>();
        for(Hardware hardware : hardwares){
            if(hardware.getId() != null){
                Hardware result = this.update(hardware.getId(), hardware);
                results.add(result);
            }
            else{
                Hardware result = this.create(hardware);
                results.add(result);
            }
        }
        return results;
    }

    @Transactional
    @Override
    public Hardware create(Hardware hardware) {
        this.objectLoggerService.logInfo("Creating hardware", hardware);
        if(hardware.getDesiredState() == null){
            HardwareState hardwareState = this.hardwareStateBuilder.getOffHardwareState();
            hardware.setDesiredState(hardwareState);
        }
        if(hardware.getCurrentState() == null){
            HardwareState hardwareState = this.hardwareStateBuilder.getOffHardwareState();
            hardware.setCurrentState(hardwareState);
        }
        HardwareEntity result = this.hardwareDAO.createHardware(hardware);
        this.hardwareQueueService.registerHardware(result);
        if(hardware.getTimers() != null) {
            hardware.getTimers().forEach((timer) -> {
                timer.setHardwareId(result.getId());
                this.timerAdditionService.create(timer);
            });
        }
        return this.hardwareConverter.toModel(result);
    }

    @Transactional
    @Override
    public void delete(long hardwareId) {
        HardwareEntity hardware = this.hardwareDAO.getHardware(hardwareId);
        this.hardwareQueueService.deregisterHardware(hardware);
        this.hardwareDAO.delete(hardwareId);
    }

    @Override
    public Hardware update(long hardwareId, Hardware hardware) {
        hardware.setId(hardwareId);
        HardwareEntity current = this.hardwareDAO.getHardware(hardware.getId());
        if(hardware.getDesiredState() == null){
            hardware.setDesiredState(hardwareStateConverter.toModel(current.getDesiredState()));
        }
        if(hardware.getCurrentState() == null){
            hardware.setCurrentState(hardwareStateConverter.toModel(current.getCurrentState()));
        }
        boolean updateHardwareState = hardware.getDesiredState().getState() != current.getDesiredState().getState();
        HardwareEntity result = this.hardwareDAO.updateHardware(hardware);
        hardware.getTimers().forEach((Timer timer) -> {
            timer.setHardwareId(result.getId());
        });
        this.timerAdditionService.updateList(hardware.getTimers());
        if(updateHardwareState) {
            this.hardwareQueueService.sendStateToController(result);
        }
        return this.hardwareConverter.toModel(result);
    }

    @Override
    public Timer addTimer(long hardwareId, Timer timer) {
        timer.setHardwareId(hardwareId);
        return this.timerAdditionService.create(timer);
    }
}
