package urbanjungletech.hardwareservice.helpers.mock.sensor;

import urbanjungletech.hardwareservice.model.sensor.Sensor;


public class MockSensor extends Sensor {
    public MockSensor() {
        this.setName("MockSensor");
        this.setPort("1");
    }
}
