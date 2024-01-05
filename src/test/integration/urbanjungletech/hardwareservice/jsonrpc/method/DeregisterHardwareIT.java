package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.helpers.services.mqtt.MqttTestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeregisterHardwareIT {



    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;

    @Autowired
    private ObjectMapper objectMapper;

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
        HardwareController hardwareController = this.hardwareControllerTestService.createMqttHardwareController();
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        Map<String, Object> params = new HashMap<>();
        params.put("port", createdHardware.getPort());
        params.put("serialNumber", createdHardwareController.getSerialNumber());
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("DeregisterHardware", params);

        String rpcMessage = objectMapper.writeValueAsString(jsonRpcMessage);

        this.mqttTestService.sendMessage(rpcMessage);

        await().atMost(3, java.util.concurrent.TimeUnit.SECONDS).untilAsserted(() -> {
            String hardwareResponseJson = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId() + "/hardware"))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
            List<Hardware> hardwareList = objectMapper.readValue(hardwareResponseJson, List.class);
            assertTrue(hardwareList.isEmpty());
        });
    }
}
