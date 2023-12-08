package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.mqtt.MqttTestService;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RegisterHardwareControllerIT {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;

    /**
     * Given a HardwareController object with the serial number 1234
     * When a json payload of the form:
     * {
     * "jsonrpc": "2.0",
     * "method": "RegisterHardwareController",
     * "params": {
     * "hardwareController":{
     * "serialNumber":"1234"
     * }
     * }
     * When the payload is sent to the mqtt topic "FromMicrocontroller"
     * Then the HardwareController object is persisted in the database
     */
    @Test
    public void testRegisterHardwareController() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("mqtt");
        hardwareController.getConfiguration().put("serialNumber", "1234");
        hardwareController.getConfiguration().put("server", "tcp://localhost:1883");
        hardwareController.getConfiguration().put("clientId", "hardwareController");
        Map<String, Object> params = new HashMap<>();
        params.put("hardwareController", hardwareController);
        params.put("serialNumber", "1234");
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("RegisterHardwareController", params);
        String mqttPayload = this.objectMapper.writeValueAsString(jsonRpcMessage);
        this.mqttTestService.sendMessage(mqttPayload);

        await()
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> {
                    assertTrue(this.hardwareControllerTestService.g
                });

    }

}
