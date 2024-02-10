package urbanjungletech.hardwareservice.helpers.mock.converter;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensor.SpecificSensorConverter;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensor;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensorEntity;

@Service
public class MockSensorConverter implements SpecificSensorConverter<MockSensor, MockSensorEntity> {
    @Override
    public MockSensor toModel(MockSensorEntity sensor) {
        return new MockSensor();
    }

    @Override
    public MockSensorEntity createEntity(MockSensor sensor) {
        return new MockSensorEntity();
    }

    @Override
    public void fillEntity(MockSensorEntity sensorEntity, MockSensor sensor) {

    }
}
