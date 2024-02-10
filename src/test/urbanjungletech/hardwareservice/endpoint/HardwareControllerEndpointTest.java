package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardware;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HardwareControllerEndpointTest {


    @Mock
    ObjectMapper objectMapper;
    @Mock
    HardwareControllerAdditionService hardwareControllerAdditionService;
    @InjectMocks
    private HardwareControllerEndpoint hardwareControllerEndpoint;
    @Test
    void createHardware() {
        int hardwareId = 1;
        Hardware hardware = new MockHardware();
        Hardware expectedHardware = new MockHardware();

        when(this.hardwareControllerAdditionService.addHardware(hardwareId, hardware)).thenReturn(expectedHardware);
        ResponseEntity<Hardware> result = this.hardwareControllerEndpoint.addHardware(hardwareId, hardware);

        verify(this.hardwareControllerAdditionService).addHardware(hardwareId, hardware);
        assertEquals(201, result.getStatusCodeValue());
        assertSame(expectedHardware, result.getBody());
    }
}
