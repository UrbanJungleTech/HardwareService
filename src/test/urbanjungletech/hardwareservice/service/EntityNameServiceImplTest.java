package urbanjungletech.hardwareservice.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.config.EntityNameConfiguration;
import urbanjungletech.hardwareservice.exception.exception.NameNotFoundException;
import urbanjungletech.hardwareservice.exception.service.EntityNameServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityNameServiceImplTest {

    @Mock
    private EntityNameConfiguration entityNameConfiguration;
    @InjectMocks
    private EntityNameServiceImpl entityNameService;
    @Test
    void getName_shouldReturnName_whenClassIsRegistered() {
        Map<String, String> names = new HashMap<>();
        String expectedName = "Object";
        names.put("java.lang.Object", expectedName);

        when(entityNameConfiguration.getNames()).thenReturn(names);

        String result = this.entityNameService.getName(Object.class);

        assertEquals(expectedName, result);
    }

    @Test
    void getName_shouldReturnCorrectName_whenMultipleClassesAreRegistered() {
        Map<String, String> names = new HashMap<>();
        String expectedName1 = "Object";
        String expectedName2 = "String";
        names.put("java.lang.Object", expectedName1);
        names.put("java.lang.String", expectedName2);

        when(entityNameConfiguration.getNames()).thenReturn(names);

        String result = this.entityNameService.getName(Object.class);

        assertEquals(expectedName1, result);
    }

    @Test
    void getName_shouldReturnCorrectName_whenMultipleClassesAreRegisteredWithSameNameButDifferentPackage() {
        Map<String, String> names = new HashMap<>();

        String expectedName1 = "UtilDate";
        String expectedName2 = "SQLDate";
        names.put("java.util.Date", expectedName1);
        names.put("java.sql.Date", expectedName2);

        when(entityNameConfiguration.getNames()).thenReturn(names);

        String result1 = this.entityNameService.getName(java.util.Date.class);
        assertEquals(expectedName1, result1);

        String result2 = this.entityNameService.getName(java.sql.Date.class);
        assertEquals(expectedName2, result2);
    }

    @Test
    void getName_shouldThrowNameNotFoundException_whenClassIsNotRegistered() {
        Map<String, String> names = new HashMap<>();

        when(this.entityNameConfiguration.getNames()).thenReturn(names);

        assertThrows(NameNotFoundException.class, () -> this.entityNameService.getName(Object.class));
    }

}
