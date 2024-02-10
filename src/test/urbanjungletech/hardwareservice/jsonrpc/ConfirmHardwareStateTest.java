package urbanjungletech.hardwareservice.jsonrpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.hardware.HardwareConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.jsonrpc.method.ConfirmHardwareState;

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
