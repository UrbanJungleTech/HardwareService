package frentz.daniel.hardwareservice.jsonrpc;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.jsonrpc.method.DeregisterHardware;
import frentz.daniel.hardwareservice.service.HardwareService;
import frentz.daniel.hardwareservice.client.model.Hardware;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeregisterHardwareTest {

    @Mock
    private HardwareAdditionService hardwareAdditionService;
    @Mock
    private HardwareService hardwareService;
    @InjectMocks
    private DeregisterHardware deregisterHardware;
    @Test
    public void process_shouldDeleteHardware_whenHardwareIsFound(){
        long hardwareId = 1;
        Hardware hardware = new Hardware();
        hardware.setId(hardwareId);

        Map<String, Object> params = new HashMap<>();
        String expectedSerialNumber = "serialNumber";
        int expectedPort = 1;
        params.put("serialNumber", expectedSerialNumber);
        params.put("hardwarePort", expectedPort);

        when(hardwareService.getHardware(anyString(), anyInt())).thenReturn(hardware);

        deregisterHardware.process(params);
        verify(hardwareAdditionService).delete(hardwareId);
    }
}
