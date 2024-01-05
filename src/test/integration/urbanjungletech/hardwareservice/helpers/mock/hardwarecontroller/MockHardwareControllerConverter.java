package urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;

@Service
public class MockHardwareControllerConverter implements SpecificHardwareControllerConverter<MockHardwareController, MockHardwareControllerEntity> {

    @Override
    public void fillEntity(MockHardwareControllerEntity hardwareControllerEntity, MockHardwareController hardwareController) {

    }

    @Override
    public MockHardwareController toModel(MockHardwareControllerEntity hardwareControllerEntity) {
        return new MockHardwareController();
    }

    @Override
    public MockHardwareControllerEntity createEntity(MockHardwareController hardwareController) {
        return new MockHardwareControllerEntity();
    }
}
