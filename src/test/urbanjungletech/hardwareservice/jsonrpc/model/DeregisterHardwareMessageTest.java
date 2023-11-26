package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.Hardware;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeregisterHardwareMessageTest {

    @Test
    void testParametersSetOnConstruction(){
        Hardware hardwareEntity = new Hardware();
        String port = "1";
        hardwareEntity.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardwareEntity);
        long setPort = (long)deregisterHardwareMessage.getParams().get("port");
        assertEquals(port, setPort);
    }

    @Test
    void testMethodNameSet(){
        Hardware hardware = new Hardware();
        String port = "1";
        hardware.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardware);
        assertEquals("DeregisterHardware", deregisterHardwareMessage.getMethod());
    }
}
