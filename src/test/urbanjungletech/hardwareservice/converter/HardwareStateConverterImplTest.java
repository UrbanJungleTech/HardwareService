package urbanjungletech.hardwareservice.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.implementation.HardwareStateConverterImpl;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.model.HardwareState;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        hardwareStateEntity.setState("on");
        hardwareStateEntity.setLevel(1);
        hardwareStateEntity.setId(1L);
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setId(1L);
        hardwareStateEntity.setHardware(hardwareEntity);
        HardwareState result = this.hardwareStateConverter.toModel(hardwareStateEntity);
        assertEquals(hardwareStateEntity.getState(), result.getState());
        assertEquals(hardwareStateEntity.getId(), result.getId());
        assertEquals(hardwareStateEntity.getLevel(), result.getLevel());
    }

    @Test
    void fillEntity() {
        HardwareState hardwareState = new HardwareState();
        hardwareState.setLevel(1);
        hardwareState.setState("on");
        HardwareStateEntity result = this.hardwareStateConverter.toEntity(hardwareState);
        assertEquals(null, result.getId());
        Assertions.assertEquals(hardwareState.getState(), result.getState());
        assertEquals(hardwareState.getLevel(), result.getLevel());
    }
}
