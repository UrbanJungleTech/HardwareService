package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.helpers.services.mqtt.MqttTestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        HardwareController hardwareController = this.hardwareControllerTestService.createMqttHardwareController();
        Map<String, Object> params = new HashMap<>();
        params.put("hardwareController", hardwareController);
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("RegisterHardwareController", params);
        String mqttPayload = this.objectMapper.writeValueAsString(jsonRpcMessage);
        this.mqttTestService.sendMessage(mqttPayload);

        await().atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<HardwareController> hardwareControllers = this.hardwareControllerTestService.findAllHardwareControllers();
                    assertTrue(hardwareControllers.size() >= 1);
                    HardwareController hardwareControllerResponse = hardwareControllers.get(0);
                    assertTrue(hardwareControllerResponse.getSerialNumber().equals("1234"));
                });
    }

}
