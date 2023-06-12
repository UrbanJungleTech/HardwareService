package frentz.daniel.hardwareservice.builder;

import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.client.model.ONOFF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HardwareStateBuilderImplTest {

    private HardwareStateBuilder hardwareStateBuilder;

    @BeforeEach
    public void setup(){
        this.hardwareStateBuilder = new HardwareStateBuilderImpl();
    }

    @Test
    void getOffHardwareState() {
        HardwareState result = this.hardwareStateBuilder.getOffHardwareState();
        assertEquals(ONOFF.OFF, result.getState());
        assertEquals(0, result.getLevel());
    }
}
