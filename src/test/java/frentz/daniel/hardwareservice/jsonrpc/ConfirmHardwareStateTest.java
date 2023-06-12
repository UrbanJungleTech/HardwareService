package frentz.daniel.hardwareservice.jsonrpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.jsonrpc.method.ConfirmHardwareState;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ConfirmHardwareStateTest {

    @Mock
    HardwareDAO hardwareDAO;
    @Mock
    HardwareAdditionService hardwareAdditionService;
    @Mock
    ObjectMapper objectMapper;
    @Mock
    HardwareConverter hardwareConverter;
    @Mock
    HardwareStateConverter hardwareStateConverter;
    @InjectMocks
    private ConfirmHardwareState confirmHardwareState;
}
