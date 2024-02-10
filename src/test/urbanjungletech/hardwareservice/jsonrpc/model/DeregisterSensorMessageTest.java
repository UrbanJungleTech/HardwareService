package urbanjungletech.hardwareservice.jsonrpc.model;

import org.junit.jupiter.api.Test;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensor;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeregisterSensorMessageTest {
    @Test
    void testParametersSetOnConstruction(){
        Sensor sensor = new MockSensor();
        String port = "1";
        sensor.setPort(port);
        DeregisterSensorMessage deregisterHardwareMessage = new DeregisterSensorMessage(sensor);
        String setPort = (String)deregisterHardwareMessage.getParams().get("port");
        assertEquals(port, setPort);
    }

    @Test
    void testMethodNameSet(){
        Sensor sensor = new MockSensor();
        String port = "1";
        sensor.setPort(port);
        DeregisterSensorMessage deregisterSensorMessage = new DeregisterSensorMessage(sensor);
        assertEquals("DeregisterSensor", deregisterSensorMessage.getMethod());
    }
}
