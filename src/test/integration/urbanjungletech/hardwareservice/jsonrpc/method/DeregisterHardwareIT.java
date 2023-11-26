package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.annotation.DirtiesContext;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.mqtt.MqttTestService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.HardwareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeregisterHardwareIT {

    @Autowired
    private HardwareRepository hardwareRepository;

    @BeforeEach
    public void setup() {
        this.hardwareRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private HardwareTestService hardwareTestService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;

    @BeforeEach
    public void setUp() {
        this.hardwareControllerRepository.deleteAll();
    }
    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234" and a Hardware with the port 1
     * When a json payload of the form:
     * {
     *  "jsonrpc": "2.0",
     *  "method": "DeregisterHardware",
     *  "params": {
     *    "serialNumber":"1234"
     *    "port":1
     *    }
     *   }
     *    is delivered to the mqtt topic "FromMicrocontroller"
     *    Then the Hardware should be deleted.
     */
    @Test
    public void testDeregisterHardware() throws Exception{
        HardwareController hardwareController = this.hardwareTestService.createBasicHardware();

        Map<String, Object> params = new HashMap<>();
        params.put("serialNumber", hardwareController.getConfiguration().get("serialNumber"));
        params.put("port", hardwareController.getHardware().get(0).getPort());
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("DeregisterHardware", params);

        String rpcMessage = objectMapper.writeValueAsString(jsonRpcMessage);

        this.mqttTestService.sendMessage(rpcMessage);

        boolean isHardwareDeleted = false;
        long timeoutMillis = 3000;
        long sleepMillis = 500;
        long timeWaitedMillis = 0;
        while (!isHardwareDeleted && timeWaitedMillis < timeoutMillis) {
            Thread.sleep(sleepMillis);
            timeWaitedMillis += sleepMillis;
            MvcResult hardwareResponse = mockMvc.perform(get("/hardwarecontroller/" + hardwareController.getId() + "/hardware"))
                    .andReturn();
            if (hardwareResponse.getResponse().getStatus() == HttpStatus.OK.value()) {
                List<Hardware> hardwareList = objectMapper.readValue(hardwareResponse.getResponse().getContentAsString(), List.class);
                isHardwareDeleted = hardwareList.isEmpty();
            }
        }
        assertTrue(isHardwareDeleted);
    }
}
