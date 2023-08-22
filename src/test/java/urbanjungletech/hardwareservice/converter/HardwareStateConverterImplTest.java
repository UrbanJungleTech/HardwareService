package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.converter.implementation.HardwareStateConverterImpl;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ONOFF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HardwareStateConverterImplTest {

    private HardwareStateConverter hardwareStateConverter;

    @BeforeEach
    public void setup(){
        this.hardwareStateConverter = new HardwareStateConverterImpl();
    }

    @Test
    void toModel() {
        HardwareStateEntity hardwareStateEntity = new HardwareStateEntity();
        hardwareStateEntity.setState(ONOFF.ON);
        hardwareStateEntity.setLevel(1);
        HardwareState result = this.hardwareStateConverter.toModel(hardwareStateEntity);
        Assertions.assertEquals(hardwareStateEntity.getState(), result.getState());
        assertEquals(hardwareStateEntity.getLevel(), result.getLevel());
    }

    @Test
    void toEntity() {
        HardwareState hardwareState = new HardwareState();
        hardwareState.setLevel(1);
        hardwareState.setState(ONOFF.ON);
        HardwareStateEntity result = this.hardwareStateConverter.toEntity(hardwareState);
        assertEquals(0, result.getId());
        Assertions.assertEquals(hardwareState.getState(), result.getState());
        assertEquals(hardwareState.getLevel(), result.getLevel());
    }
}
