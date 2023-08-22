package urbanjungletech.hardwareservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.HardwareService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HardwareEndpointTest {
    @Mock
    HardwareAdditionService hardwareAdditionService;
    @Mock
    HardwareService hardwareService;
    @Mock
    HardwareControllerAdditionService hardwareControllerAdditionService;
    @InjectMocks
    private HardwareEndpoint hardwareEndpoint;
    @Test
    void getHardware_shouldReturnHardwareThatCorrespondsToTheIdPassedIntoHardwareService_responseStatusShouldBe200() {
        int hardwareControllerId = 1;
        Hardware expectedHardware = new Hardware();

        when(this.hardwareService.getHardware(hardwareControllerId)).thenReturn(expectedHardware);

        ResponseEntity<Hardware> result = this.hardwareEndpoint.getHardwareById(hardwareControllerId);

        verify(this.hardwareService).getHardware(hardwareControllerId);
        assertEquals(200, result.getStatusCodeValue());
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
        Hardware hardware = new Hardware();
        Hardware updatedHardware = new Hardware();

        when(this.hardwareAdditionService.update(1, hardware)).thenReturn(updatedHardware);

        ResponseEntity<Hardware> result = this.hardwareEndpoint.updateHardware(1, hardware);

        verify(this.hardwareAdditionService).update(1, hardware);
        assertEquals(200, result.getStatusCodeValue());
        assertSame(updatedHardware, result.getBody());
    }
}
