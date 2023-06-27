package frentz.daniel.hardwareservice.helper;

import frentz.daniel.hardwareservice.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardwareHelper {
    public static Hardware createHardware(){
        Hardware hardware = new Hardware();
        hardware.setPort(1L);
        hardware.setName("hardware");
        hardware.setId(2L);

        Timer timer1 = new Timer();
        Timer timer2 = new Timer();

        hardware.setTimers(List.of(timer1, timer2));
        HardwareState onState = new HardwareState();
        onState.setState(ONOFF.ON);
        onState.setLevel(1);
        hardware.setDesiredState(onState);
        HardwareState offState = new HardwareState();
        offState.setState(ONOFF.OFF);
        offState.setLevel(0);
        hardware.setCurrentState(offState);
        hardware.setType("HEATER");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("first", "first_value");
        metadata.put("second", "second_value");
        hardware.setMetadata(metadata);

        return hardware;
    }
}
