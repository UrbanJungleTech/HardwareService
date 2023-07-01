package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.MqttTestService;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterHardwareControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;

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
        hardwareController.setSerialNumber("1234");
        String payload = this.objectMapper.writeValueAsString(hardwareController);
        Map<String, Object> params = new HashMap<>();
        params.put("hardwareController", hardwareController);
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("RegisterHardwareController", params);
        String mqttPayload = this.objectMapper.writeValueAsString(jsonRpcMessage);
        this.mqttTestService.sendMessage(mqttPayload);
        boolean isConfirmed = false;
        long timeoutMillis = 3000;
        long sleepMillis = 500;
        long timeWaitedMillis = 0;
        while (!isConfirmed && timeWaitedMillis < timeoutMillis) {
            Thread.sleep(sleepMillis);
            timeWaitedMillis += sleepMillis;
            if(this.hardwareControllerRepository.findBySerialNumber("1234") != null)
                isConfirmed = true;
        }
        assertTrue(isConfirmed);
    }

}