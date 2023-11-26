package urbanjungletech.hardwareservice.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ONOFF;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HardwareStateBuilderImplTest {

    private HardwareStateBuilder hardwareStateBuilder;

    @BeforeEach
    public void setup(){
        this.hardwareStateBuilder = new HardwareStateBuilderImpl();
    }

    @Test
    void getOffHardwareState() {
        HardwareState result = this.hardwareStateBuilder.getOffHardwareState();
        Assertions.assertEquals(ONOFF.OFF, result.getState());
        assertEquals(0, result.getLevel());
    }
}
