package urbanjungletech.hardwareservice.jsonrpc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.jsonrpc.method.DeregisterHardware;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeregisterHardwareTest {

    @Mock
    private HardwareAdditionService hardwareAdditionService;
    @Mock
    private HardwareQueryService hardwareQueryService;
    @InjectMocks
    private DeregisterHardware deregisterHardware;
    @Test
    public void process_shouldDeleteHardware_whenHardwareIsFound(){
        long hardwareId = 1;
        Hardware hardware = new Hardware();
        hardware.setId(hardwareId);

        Map<String, Object> params = new HashMap<>();
        String expectedSerialNumber = "serialNumber";
        String expectedPort = "1";
        params.put("serialNumber", expectedSerialNumber);
        params.put("port", expectedPort);

        when(hardwareQueryService.getHardware(anyString(), anyString())).thenReturn(hardware);

        deregisterHardware.process(params);
        verify(hardwareAdditionService).delete(hardwareId);
    }
}
