package urbanjungletech.hardwareservice.controller.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.controller.HardwareEndpoint;
import urbanjungletech.hardwareservice.helper.HardwareHelper;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.HardwareService;

@ExtendWith(MockitoExtension.class)
class HardwareEndpointTest {


    @Mock
    HardwareAdditionService hardwareAdditionService;

    @Mock
    HardwareService hardwareService;

    HardwareEndpoint hardwareController;

    Hardware hardware;

    ObjectMapper objectMapper;

    String hardwareJson;

    @BeforeEach
    public void setup() throws Exception{
        objectMapper = new ObjectMapper();
        hardware = HardwareHelper.createHardware();
        hardwareJson = objectMapper.writeValueAsString(hardware);
    }


}
