package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.entity.SensorEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeregisterSensorMessageTest {
    @Test
    void testParametersSetOnConstruction(){
        SensorEntity sensorEntity = new SensorEntity();
        long port = 1;
        sensorEntity.setPort(port);
        DeregisterSensorMessage deregisterHardwareMessage = new DeregisterSensorMessage(sensorEntity);
        long setPort = (long)deregisterHardwareMessage.getParams().get("port");
        assertEquals(port, setPort);
    }

    @Test
    void testMethodNameSet(){
        SensorEntity sensorEntity = new SensorEntity();
        long port = 1;
        sensorEntity.setPort(port);
        DeregisterSensorMessage deregisterSensorMessage = new DeregisterSensorMessage(sensorEntity);
        assertEquals("DeregisterSensor", deregisterSensorMessage.getMethod());
    }
}
