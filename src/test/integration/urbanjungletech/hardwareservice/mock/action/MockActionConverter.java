package urbanjungletech.hardwareservice.mock.action;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;

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
