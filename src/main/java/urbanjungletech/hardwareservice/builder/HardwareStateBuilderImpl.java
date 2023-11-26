package urbanjungletech.hardwareservice.builder;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ONOFF;

@Service
public class HardwareStateBuilderImpl implements HardwareStateBuilder{
    @Override
    public HardwareState getOffHardwareState() {
        HardwareState result = new HardwareState();
        result.setState(ONOFF.OFF);
        result.setLevel(0);
        return result;
    }
}
