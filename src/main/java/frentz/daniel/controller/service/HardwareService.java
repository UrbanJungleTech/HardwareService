package frentz.daniel.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controller.entity.HardwareEntity;
import frentz.daniel.controllerclient.model.Hardware;
import frentz.daniel.controllerclient.model.HardwareState;
import frentz.daniel.controllerclient.model.Timer;

public interface HardwareService {
    void createHardware(Hardware hardware);
    HardwareEntity createAndSaveHardware(Hardware hardware, HardwareControllerEntity hardwareControllerEntity);
    Hardware setDesiredState(HardwareEntity hardwareEntity, HardwareState hardwareState);
    Hardware setStateConfirmation(String hardwareSerialNumber, long port, HardwareState hardwareState);
    Hardware getHardware(long hardwareId);
    Hardware addTimer(long hardwareId, Timer timer) throws JsonProcessingException;
    Hardware getHardwareByPortAndSerialNumber(long port, String hardwareControllerSerialNumber);
}
