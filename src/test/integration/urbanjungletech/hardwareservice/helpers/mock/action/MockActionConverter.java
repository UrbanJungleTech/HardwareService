package urbanjungletech.hardwareservice.helpers.mock.action;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.endpoint.MockEntity;

@Service
public class MockActionConverter implements SpecificAlertActionConverter<MockAction, MockEntity> {

    @Override
    public MockAction toModel(MockEntity actionEntity) {
        return new MockAction();
    }

    @Override
    public void fillEntity(MockEntity entity, MockAction mockAction) {

    }

    @Override
    public MockEntity createEntity(MockAction mockAction) {
        return new MockEntity();
    }
}
