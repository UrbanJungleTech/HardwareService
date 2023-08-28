package urbanjungletech.hardwareservice.jsonrpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.jsonrpc.method.ConfirmHardwareState;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
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