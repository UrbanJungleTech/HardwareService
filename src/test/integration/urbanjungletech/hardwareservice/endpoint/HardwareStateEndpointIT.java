package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "development.mqtt.client.enabled=false",
        "mqtt.server.enabled=false",
        "mqtt-rpc.enabled=false"})
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
    private HardwareControllerTestService hardwareControllerTestService;



    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a GET request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 200 OK
     * And the response body should be the desiredState of the hardware
     */
    @Test
    public void getHardwareStateById() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        HardwareState desiredState = createdHardwareController.getHardware().get(0).getDesiredState();

        String responseJson = this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        HardwareState responseHardwareState = objectMapper.readValue(responseJson, HardwareState.class);
        assertEquals(desiredState.getLevel(), responseHardwareState.getLevel());
        assertEquals(desiredState.getState(), responseHardwareState.getState());
        assertEquals(desiredState.getHardwareId(), responseHardwareState.getHardwareId());
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
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardwareController.addHardware(hardware);
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        HardwareState desiredState = createdHardwareController.getHardware().get(0).getDesiredState();
        desiredState.setState("on");
        desiredState.setLevel(50);

        String updatedHardwareStateJson = this.mockMvc.perform(put("/hardwarestate/" + desiredState.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(desiredState)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        HardwareState updatedHardwareState = objectMapper.readValue(updatedHardwareStateJson, HardwareState.class);
        assertEquals(desiredState.getLevel(), updatedHardwareState.getLevel());
        assertEquals(desiredState.getState(), updatedHardwareState.getState());
        assertEquals(desiredState.getHardwareId(), updatedHardwareState.getHardwareId());
        assertEquals(desiredState.getId(), updatedHardwareState.getId());

        String hardwareStateResponseJson = this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HardwareState hardwareStateResponse = objectMapper.readValue(hardwareStateResponseJson, HardwareState.class);
        assertEquals(desiredState.getLevel(), hardwareStateResponse.getLevel());
        assertEquals(desiredState.getState(), hardwareStateResponse.getState());
        assertEquals(desiredState.getHardwareId(), hardwareStateResponse.getHardwareId());
        assertEquals(desiredState.getId(), hardwareStateResponse.getId());

        await()
                .atMost(Duration.of(3, ChronoUnit.SECONDS))
                .with()
                .untilAsserted(() ->
                        {
                            String responseHardwareStateJson = this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                                        .andExpect(status().isOk())
                                    .andReturn().getResponse().getContentAsString();
                            HardwareState responseHardwareState = objectMapper.readValue(responseHardwareStateJson, HardwareState.class);
                            assertEquals(desiredState.getLevel(), responseHardwareState.getLevel());
                            assertEquals(desiredState.getState(), responseHardwareState.getState());
                            assertEquals(desiredState.getHardwareId(), responseHardwareState.getHardwareId());
                            assertEquals(desiredState.getId(), responseHardwareState.getId());

                        });

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
