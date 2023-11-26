package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HardwareStateEndpointIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HardwareTestService hardwareTestService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;
    @Autowired
    private MockMqttClientListener mockMqttClientListener;
    @BeforeEach
    public void setup(){
        this.mockMqttClientListener.clear();
        hardwareControllerRepository.deleteAll();
        hardwareControllerRepository.flush();
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a GET request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 200 OK
     * And the response body should be the desiredState of the hardware
     */
    @Test
    public void getHardwareStateById() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        HardwareState desiredState = createdHardwareController.getHardware().get(0).getDesiredState();

        this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(desiredState.getId()))
                .andExpect(jsonPath("$.hardwareId").value(desiredState.getHardwareId()))
                .andExpect(jsonPath("$.state").value(desiredState.getState().toString()));
    }

    /**
     * Given an id which is not associatd with a hardware state
     * When a GET request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 404 NOT FOUND
     */
    @Test
    public void getHardwareStateByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/hardwarestate/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a PUT request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 200 OK
     * And the response body should be the updated desiredState of the hardware
     * And a GET call to /hardwarestate should return the updated desiredState
     */
    @Test
    public void updateHardwareState() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        HardwareState desiredState = createdHardwareController.getHardware().get(0).getDesiredState();
        desiredState.setState(ONOFF.ON);
        desiredState.setLevel(50);

        this.mockMvc.perform(put("/hardwarestate/" + desiredState.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(desiredState)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(desiredState.getId()))
                .andExpect(jsonPath("$.hardwareId").value(desiredState.getHardwareId()))
                .andExpect(jsonPath("$.state").value(desiredState.getState().toString()))
                .andExpect(jsonPath("$.level").value(desiredState.getLevel()));

        this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(desiredState.getId()))
                .andExpect(jsonPath("$.hardwareId").value(desiredState.getHardwareId()))
                .andExpect(jsonPath("$.state").value(desiredState.getState().toString()))
                .andExpect(jsonPath("$.level").value(desiredState.getLevel()));

        await()
                .atMost(Duration.of(10, ChronoUnit.SECONDS))
                .with()
                .untilAsserted(() -> assertEquals(1, this.mockMqttClientListener.getCache("StateChange").size()));
        List<JsonRpcMessage> results = this.mockMqttClientListener.getCache("StateChange");
        assertEquals(1, results.size());
        JsonRpcMessage message = results.get(0);
        HardwareState hardwareState = objectMapper.convertValue(message.getParams().get("desiredState"), HardwareState.class);
        assertEquals(desiredState.getLevel(), hardwareState.getLevel());
        assertEquals(desiredState.getState(), hardwareState.getState());
        assertEquals(desiredState.getHardwareId(), hardwareState.getHardwareId());
        assertEquals(desiredState.getId(), hardwareState.getId());
    }

    /**
     * Given an id which is not associatd with a hardware state
     * When a PUT request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 404 NOT FOUND
     */
    @Test
    public void updateHardwareStateNotFound() throws Exception {
        HardwareState desiredState = new HardwareState();

        this.mockMvc.perform(put("/hardwarestate/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(desiredState)))
                .andExpect(status().isNotFound());
    }

}
