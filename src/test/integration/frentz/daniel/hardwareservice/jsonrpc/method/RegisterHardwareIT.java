package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.HardwareTestService;
import frentz.daniel.hardwareservice.MqttTestService;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.service.mqtt.MqttClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterHardwareIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HardwareRepository hardwareRepository;

    @Autowired
    MqttTestService mqttTestService;

    @Autowired
    HardwareTestService hardwareTestService;

    @Autowired
    MqttClient mqttClient;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;

    @BeforeEach
    public void setUp() {
        this.hardwareRepository.deleteAll();
        this.hardwareControllerRepository.deleteAll();
    }

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
        HardwareController hardwareControllerResponse = this.hardwareTestService.createBasicHardware();

        Hardware hardware = new Hardware();
        hardware.setPort(1L);
        hardware.setType("light");

        String hardwareJson = objectMapper.writeValueAsString(hardware);

        this.mqttTestService.sendMessage(hardwareJson);

        boolean isHardwareCreated = false;
        long timeoutMillis = 30000;
        long sleepMillis = 500;
        long timeWaitedMillis = 0;
        while (!isHardwareCreated && timeWaitedMillis < timeoutMillis) {
            Thread.sleep(sleepMillis);
            timeWaitedMillis += sleepMillis;
            MvcResult hardwareResponse = mockMvc.perform(get("/hardwarecontroller/" + hardwareControllerResponse.getId() + "/hardware"))
                    .andReturn();
            if (hardwareResponse.getResponse().getStatus() == HttpStatus.OK.value()) {
                List<Hardware> hardwareList = objectMapper.readValue(hardwareResponse.getResponse().getContentAsString(), List.class);
                isHardwareCreated = !hardwareList.isEmpty();
            }
        }
        assertTrue(isHardwareCreated);
    }
}
