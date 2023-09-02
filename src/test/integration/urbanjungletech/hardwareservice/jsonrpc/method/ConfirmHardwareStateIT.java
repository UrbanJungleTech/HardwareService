package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.HardwareTestService;
import urbanjungletech.hardwareservice.MqttTestService;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConfirmHardwareStateIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MqttTestService mqttTestService;

    @Autowired
    private HardwareTestService hardwareTestService;

    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;
    @BeforeEach
    public void setup() throws Exception {
        this.hardwareControllerRepository.deleteAll();
    }

    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234" and a Hardware with the port 1
     * When a PUT request is made to /hardware/{hardwareId}/state with a payload of the form:
     * {
     * "level":2,
     * "state": "ON"
     * }
     * Then a json payload of the form:
     * {
     * "jsonrpc": "2.0",
     * "method": "ConfirmHardwareState",
     * "params": {
     * "serialNumber":"1234",
     * "port": {port},
     * "state": "ON",
     * "level": 2
     * }
     * }
     */
    @Test
    public void confirmHardwareState() throws Exception {
        HardwareController controller = this.hardwareTestService.createBasicHardware();
        Hardware hardware = controller.getHardware().get(0);

        //generate json payload for updating state
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage();
        jsonRpcMessage.setMethod("ConfirmHardwareState");
        Map<String, Object> params = new HashMap<>();
        params.put("serialNumber", "1234");
        params.put("port", 1);
        HardwareState hardwareState = new HardwareState();
        hardwareState.setLevel(2);
        hardwareState.setState(ONOFF.OFF);
        params.put("hardwareState", hardwareState);
        jsonRpcMessage.setParams(params);
        String mqttPayload = this.objectMapper.writeValueAsString(jsonRpcMessage);

        //send message to mqtt broker
        this.mqttTestService.sendMessage(mqttPayload);
        //wait for the current state to be updated
        boolean isConfirmed = false;
        long timeoutMillis = 3000;
        long sleepMillis = 500;
        long timeWaitedMillis = 0;
        while (!isConfirmed && timeWaitedMillis < timeoutMillis) {
            Thread.sleep(sleepMillis);
            timeWaitedMillis += sleepMillis;
            MvcResult hardwareResponse = mockMvc.perform(get("/hardware/" + hardware.getId()))
                    .andReturn();
            if (hardwareResponse.getResponse().getStatus() == HttpStatus.OK.value()) {
                Hardware updatedHardware = objectMapper.readValue(hardwareResponse.getResponse().getContentAsString(), Hardware.class);
                HardwareState state = updatedHardware.getCurrentState();
                isConfirmed = state.getState() == ONOFF.OFF && state.getLevel() == 2;
            }
        }
        assertTrue(isConfirmed);
    }
}
