package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeregisterSensorMessageTest {
    @Test
    void testParametersSetOnConstruction(){
        Sensor sensor = new Sensor();
        long port = 1;
        sensor.setPort(port);
        DeregisterSensorMessage deregisterHardwareMessage = new DeregisterSensorMessage(sensor);
        long setPort = (long)deregisterHardwareMessage.getParams().get("port");
        assertEquals(port, setPort);
    }

    @Test
    void testMethodNameSet(){
        Sensor sensor = new Sensor();
        long port = 1;
        sensor.setPort(port);
        DeregisterSensorMessage deregisterSensorMessage = new DeregisterSensorMessage(sensor);
        assertEquals("DeregisterSensor", deregisterSensorMessage.getMethod());
    }
}
