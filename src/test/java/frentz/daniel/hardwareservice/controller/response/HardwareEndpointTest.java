package frentz.daniel.hardwareservice.controller.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.controller.HardwareEndpoint;
import frentz.daniel.hardwareservice.helper.HardwareHelper;
import frentz.daniel.hardwareservice.service.HardwareService;
import frentz.daniel.hardwareservice.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
