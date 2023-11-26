package urbanjungletech.hardwareservice.converter.alert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertConverterImplTest {

    @Mock
    private AlertActionConverter alertActionConverter;

    @Mock
    private AlertConditionConverter alertConditionConverter;

    @InjectMocks
    private AlertConverterImpl alertConverter;

    @Test
    public void testToModel_withNonEmptyActionsAndConditions() {
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setId(1L);
        alertEntity.setName("Test Alert");
        alertEntity.setDescription("Test Description");
        alertEntity.setActions(List.of(mock(AlertActionEntity.class)));
        alertEntity.setConditions(List.of(mock(AlertConditionEntity.class)));

        when(alertActionConverter.toModel(any())).thenReturn(mock(AlertAction.class));
        when(alertConditionConverter.toModel(any())).thenReturn(mock(AlertCondition.class));

        Alert result = alertConverter.toModel(alertEntity);

        assertEquals(alertEntity.getId(), result.getId());
        assertEquals(alertEntity.getName(), result.getName());
        assertEquals(alertEntity.getDescription(), result.getDescription());
        assertNotNull(result.getActions());
        assertNotNull(result.getConditions());
        assertEquals(1, result.getActions().size());
        assertEquals(1, result.getConditions().size());
    }

    @Test
    public void testToModel_withEmptyActionsAndConditions() {
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setId(1L);
        alertEntity.setName("Test Alert");
        alertEntity.setDescription("Test Description");
        alertEntity.setActions(Collections.emptyList());
        alertEntity.setConditions(Collections.emptyList());

        Alert result = alertConverter.toModel(alertEntity);

        assertEquals(alertEntity.getId(), result.getId());
        assertEquals(alertEntity.getName(), result.getName());
        assertEquals(alertEntity.getDescription(), result.getDescription());
        assertNotNull(result.getActions());
        assertTrue(result.getActions().isEmpty());
        assertNotNull(result.getConditions());
        assertTrue(result.getConditions().isEmpty());
    }

    @Test
    public void testFillEntity() {
        AlertEntity alertEntity = new AlertEntity();
        Alert alert = new Alert();
        alert.setName("Updated Alert Name");

        alertConverter.fillEntity(alertEntity, alert);

        assertEquals(alert.getName(), alertEntity.getName());
    }
}
