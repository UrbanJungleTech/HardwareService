package urbanjungletech.hardwareservice.helpers.mock.converter;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardware;
import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardwareEntity;

@Service
public class MockHardwareConverter implements SpecificHardwareConverter<MockHardware, MockHardwareEntity> {
    @Override
    public MockHardware toModel(MockHardwareEntity hardware) {
        return new MockHardware();
    }

    @Override
    public MockHardwareEntity createEntity(MockHardware hardware) {

        return new MockHardwareEntity();
    }

    @Override
    public void fillEntity(MockHardwareEntity hardwareEntity, MockHardware hardware) {

    }
}
