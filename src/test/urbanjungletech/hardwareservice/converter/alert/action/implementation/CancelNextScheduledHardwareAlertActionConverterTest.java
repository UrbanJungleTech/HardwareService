package urbanjungletech.hardwareservice.converter.alert.action.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.entity.alert.action.CancelNextScheduledHardwareAlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.CancelNextScheduledHardwareAlertAction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CancelNextScheduledHardwareAlertActionConverterTest {

    @InjectMocks
    private CancelNextScheduledHardwareAlertActionConverter converter;

    @Test
    public void testToModel() {
        CancelNextScheduledHardwareAlertActionEntity actionEntity = new CancelNextScheduledHardwareAlertActionEntity();
        actionEntity.setScheduledHardwareId(123L);

        CancelNextScheduledHardwareAlertAction result = converter.toModel(actionEntity);

        assertEquals(CancelNextScheduledHardwareAlertAction.class, result.getClass());
        assertEquals(actionEntity.getScheduledHardwareId(), result.getScheduledHardwareId());
    }

    @Test
    public void testFillEntity() {
        CancelNextScheduledHardwareAlertActionEntity entity = new CancelNextScheduledHardwareAlertActionEntity();
        CancelNextScheduledHardwareAlertAction action = new CancelNextScheduledHardwareAlertAction();
        action.setScheduledHardwareId(123L);

        converter.fillEntity(entity, action);

        assertEquals(action.getScheduledHardwareId(), entity.getScheduledHardwareId());
    }

    @Test
    public void testCreateEntity() {
        CancelNextScheduledHardwareAlertAction action = new CancelNextScheduledHardwareAlertAction();

        CancelNextScheduledHardwareAlertActionEntity result = converter.createEntity(action);

        assertNotNull(result);
        assertNull(result.getScheduledHardwareId());
    }
}

