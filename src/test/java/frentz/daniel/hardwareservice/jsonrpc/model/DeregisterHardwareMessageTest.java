package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeregisterHardwareMessageTest {

    @Test
    void testParametersSetOnConstruction(){
        HardwareEntity hardwareEntity = new HardwareEntity();
        long port = 1;
        hardwareEntity.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardwareEntity);
        long setPort = (long)deregisterHardwareMessage.getParams().get("port");
        assertEquals(port, setPort);
    }

    @Test
    void testMethodNameSet(){
        HardwareEntity hardwareEntity = new HardwareEntity();
        long port = 1;
        hardwareEntity.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardwareEntity);
        assertEquals("DeregisterHardware", deregisterHardwareMessage.getMethod());
    }
}
