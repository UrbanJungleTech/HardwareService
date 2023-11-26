package urbanjungletech.hardwareservice.jsonrpc.model;

import org.junit.jupiter.api.Test;
import urbanjungletech.hardwareservice.model.Hardware;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeregisterHardwareMessageTest {

    @Test
    void testParametersSetOnConstruction(){
        Hardware hardwareEntity = new Hardware();
        String port = "1";
        hardwareEntity.setPort(port);
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardwareEntity);
        String setPort = (String)deregisterHardwareMessage.getParams().get("port");
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
