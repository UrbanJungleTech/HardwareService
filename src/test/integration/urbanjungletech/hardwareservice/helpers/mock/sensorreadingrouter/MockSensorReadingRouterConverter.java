package urbanjungletech.hardwareservice.helpers.mock.sensorreadingrouter;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;

@Service
public class MockSensorReadingRouterConverter implements SpecificSensorReadingRouterConverter<MockSensorReadingRouter, MockSensorReadingRouterEntity> {

    @Override
    public MockSensorReadingRouter toModel(MockSensorReadingRouterEntity entity) {
        return new MockSensorReadingRouter();
    }

    @Override
    public MockSensorReadingRouterEntity createEntity(MockSensorReadingRouter mockSensorReadingRouter) {
        return new MockSensorReadingRouterEntity();
    }

    @Override
    public void fillEntity(MockSensorReadingRouterEntity entity, MockSensorReadingRouter mockSensorReadingRouter) {

    }
}
