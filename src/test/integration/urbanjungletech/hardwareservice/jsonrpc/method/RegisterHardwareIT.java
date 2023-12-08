package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.jsonrpc.model.RegisterHardwareMessage;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.mqtt.MqttClient;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.mqtt.MqttTestService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RegisterHardwareIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MqttTestService mqttTestService;

    @Autowired
    HardwareTestService hardwareTestService;

    @Autowired
    MqttClient mqttClient;
    @Autowired
    HardwareControllerTestService hardwareControllerTestService;


    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234"
     * When a json payload of the form:
     * {
     *    "jsonrpc": "2.0",
     *    "method": "RegisterHardware",
     *    "params": {
     *    "serialNumber":"1234",
     *    "hardware": {
     *    "port": 1,
     *    "name: "Test",
     *    "type": "light"
     *    }
     *    }
     * }
     * is delivered to the mqtt topic "FromMicrocontroller"
     * Then a Hardware with the name "Test" and type "light" should be created and associated with the HardwareController with the serial number "1234"
     */
    @Test
    public void testRegisterHardware() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        hardwareController.getConfiguration().put("serialNumber", "1234");
        HardwareController hardwareControllerResponse = this.hardwareControllerTestService.postHardwareController(hardwareController);

        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardware.getConfiguration().put("serialNumber", "1234");
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState("on");
        hardwareState.setLevel(100);
        hardware.setCurrentState(hardwareState);


        RegisterHardwareMessage registerHardwareMessage = new RegisterHardwareMessage();
        registerHardwareMessage.getParams().put("hardware", hardware);

        String messageJson = objectMapper.writeValueAsString(registerHardwareMessage);

        this.mqttTestService.sendMessage(messageJson);

        await().atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            HardwareController retrievedHardwareController = this.hardwareControllerTestService.findHardwareControllerById(hardwareControllerResponse.getId());
            assertNotNull(retrievedHardwareController);
            List<Hardware> hardwareList = retrievedHardwareController.getHardware();
            assertTrue(hardwareList.size() >= 1);
        });
    }
}
