package urbanjungletech.hardwareservice.endpoint.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.endpoint.HardwareEndpoint;
import urbanjungletech.hardwareservice.helper.HardwareHelper;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

@ExtendWith(MockitoExtension.class)
class HardwareEndpointTest {


    @Mock
    HardwareAdditionService hardwareAdditionService;

    @Mock
    HardwareQueryService hardwareQueryService;

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
