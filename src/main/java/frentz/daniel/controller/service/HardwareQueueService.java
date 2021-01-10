package frentz.daniel.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controllerclient.model.HardwareState;

public interface HardwareQueueService {
    String createStateChangeMessage(long port, HardwareState state, String serialNumber) throws JsonProcessingException;

    String getHardwareStateChangeQueue(String hardwareControllerSerialNumber);

    void createHardwareStateChangeQueue(String hardwareControllerSerialNumber);

    void sendStateToController(HardwareControllerEntity hardwareControllerEntity, long port, HardwareState state);
}
