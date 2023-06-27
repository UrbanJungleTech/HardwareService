package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeregisterHardwareMessageTest {

    @Test
    void testParametersSetOnConstruction(){
        Hardware hardwareEntity = new Hardware();
        long port = 1;
        hardwareEntity.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardwareEntity);
        long setPort = (long)deregisterHardwareMessage.getParams().get("port");
        assertEquals(port, setPort);
    }

    @Test
    void testMethodNameSet(){
        Hardware hardware = new Hardware();
        long port = 1;
        hardware.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardware);
        assertEquals("DeregisterHardware", deregisterHardwareMessage.getMethod());
    }
}
