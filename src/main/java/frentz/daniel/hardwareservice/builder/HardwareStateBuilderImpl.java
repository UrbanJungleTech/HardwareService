package frentz.daniel.hardwareservice.builder;

import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.client.model.ONOFF;
import org.springframework.stereotype.Service;

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
