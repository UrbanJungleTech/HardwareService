package urbanjungletech.hardwareservice.helpers.mock.hardware;

import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.List;

public class MockHardware extends Hardware {
    public MockHardware() {
        this.setName("MockHardware");
        this.setPort("1");
        this.setPossibleStates(List.of("on", "off"));
        this.setOffState("off");
    }
}
