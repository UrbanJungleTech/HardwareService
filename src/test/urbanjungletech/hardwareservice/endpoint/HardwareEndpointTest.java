package urbanjungletech.hardwareservice.endpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardware;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HardwareEndpointTest {
    @Mock
    HardwareAdditionService hardwareAdditionService;
    @Mock
    HardwareQueryService hardwareQueryService;
    @Mock
    HardwareControllerAdditionService hardwareControllerAdditionService;
    @InjectMocks
    private HardwareEndpoint hardwareEndpoint;
    @Test
    void getHardware_shouldReturnHardwareThatCorrespondsToTheIdPassedIntoHardwareService_responseStatusShouldBe200() {
        int hardwareControllerId = 1;
        Hardware expectedHardware = new MockHardware();

        when(this.hardwareQueryService.getHardware(hardwareControllerId)).thenReturn(expectedHardware);

        ResponseEntity<Hardware> result = this.hardwareEndpoint.getHardwareById(hardwareControllerId);

        verify(this.hardwareQueryService).getHardware(hardwareControllerId);
        assertEquals(200, result.getStatusCode().value());
        assertSame(expectedHardware, result.getBody());
    }


    @Test
    void removeHardware() {
        int hardwareId = 1;
        ResponseEntity result = this.hardwareEndpoint.deleteHardwareById(hardwareId);

        verify(this.hardwareAdditionService).delete(hardwareId);
        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
    }

    @Test
    void updateHardware() {
        Hardware hardware = new MockHardware();
        Hardware updatedHardware = new MockHardware();

        when(this.hardwareAdditionService.update(1, hardware)).thenReturn(updatedHardware);

        ResponseEntity<Hardware> result = this.hardwareEndpoint.updateHardware(1, hardware);

        verify(this.hardwareAdditionService).update(1, hardware);
        assertEquals(200, result.getStatusCodeValue());
        assertSame(updatedHardware, result.getBody());
    }
}
