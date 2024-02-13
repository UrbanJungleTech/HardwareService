package urbanjungletech.hardwareservice.helper;

import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardwareHelper {
    public static Hardware createHardware(){
        Hardware hardware = new MockHardware();
        hardware.setPort("1");
        hardware.setName("hardware");
        hardware.setId(2L);

        Timer timer1 = new Timer();
        Timer timer2 = new Timer();

        hardware.setTimers(List.of(timer1, timer2));
        HardwareState onState = new HardwareState();
        onState.setState("on");
        onState.setLevel(1);
        hardware.setDesiredState(onState);
        HardwareState offState = new HardwareState();
        offState.setState("off");
        offState.setLevel(0);
        hardware.setCurrentState(offState);
        hardware.setType("HEATER");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("first", "first_value");
        metadata.put("second", "second_value");

        return hardware;
    }
}
