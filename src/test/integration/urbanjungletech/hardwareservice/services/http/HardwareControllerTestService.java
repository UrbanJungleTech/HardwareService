package urbanjungletech.hardwareservice.services.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class HardwareControllerTestService {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    public HardwareControllerTestService(ObjectMapper objectMapper,
                                         MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    public HardwareController createBasicHardwareControllerHttp(HardwareController hardwareController) throws Exception{
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();

        HardwareController hardwareControllerResponse = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);
        return hardwareControllerResponse;
    }

    public HardwareController createBasicHardwareControllerHttp() throws Exception {
        HardwareController hardwareController = createBasicHardwareController();
        return createBasicHardwareControllerHttp(hardwareController);
    }

    public HardwareController addBasicHardwareControllerWithSensors(List<Sensor> sensors) throws Exception{
        HardwareController hardwareController = createBasicHardwareController();
        hardwareController.setSensors(sensors);
        HardwareController result = this.createBasicHardwareControllerHttp(hardwareController);
        return result;
    }

    public HardwareController addBasicHardwareControllerWithHardware(List<Hardware> hardware) throws Exception{
        HardwareController hardwareController = createBasicHardwareController();
        hardwareController.setHardware(hardware);
        HardwareController result = this.createBasicHardwareControllerHttp(hardwareController);
        return result;
    }

    public HardwareController createBasicHardwareController() {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("mqtt");
        hardwareController.getConfiguration().put("serialNumber", "1234");
        hardwareController.getConfiguration().put("server", "tcp://localhost:1883");
        hardwareController.getConfiguration().put("responseQueue", "HardwareServer");
        hardwareController.getConfiguration().put("requestQueue", "1234ToMicro");
        return hardwareController;
    }
}
