package urbanjungletech.hardwareservice.converter.alert.action.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.entity.alert.action.HardwareStateChangeAlertActionEntity;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.model.alert.action.HardwareStateChangeAlertAction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HardwareStateChangeAlertActionConverterImplTest {

    @InjectMocks
    private HardwareStateChangeAlertActionConverterImpl converter;

    @Test
    public void testToModel() {
        HardwareStateChangeAlertActionEntity actionEntity = new HardwareStateChangeAlertActionEntity();
        actionEntity.setHardwareId(123L);
        actionEntity.setOnoff(ONOFF.OFF);
        actionEntity.setLevel(5L);

        HardwareStateChangeAlertAction result = converter.toModel(actionEntity);

        assertEquals(actionEntity.getHardwareId(), result.getHardwareId());
        assertEquals(actionEntity.getOnoff(), result.getOnoff());
        assertEquals(actionEntity.getLevel(), result.getLevel());
    }

    @Test
    public void testFillEntity() {
        HardwareStateChangeAlertActionEntity entity = new HardwareStateChangeAlertActionEntity();
        HardwareStateChangeAlertAction action = new HardwareStateChangeAlertAction();
        action.setHardwareId(123L);
        action.setOnoff(ONOFF.ON);
        action.setLevel(5L);

        converter.fillEntity(entity, action);

        assertEquals(action.getHardwareId(), entity.getHardwareId());
        assertEquals(action.getOnoff(), entity.getOnoff());
        assertEquals(action.getLevel(), entity.getLevel());
    }

    @Test
    public void testCreateEntity() {
        HardwareStateChangeAlertAction action = new HardwareStateChangeAlertAction();

        HardwareStateChangeAlertActionEntity result = converter.createEntity(action);

        assertNotNull(result);
        assertNull(result.getHardwareId());
        assertNull(result.getOnoff());
        assertNull(result.getLevel());
    }
}
