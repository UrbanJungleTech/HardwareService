package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.*;
import urbanjungletech.hardwareservice.repository.HardwareRepository;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HardwareEndpointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareRepository hardwareRepository;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    HardwareTestService hardwareTestService;
    @Autowired
    MockMqttClientListener mqttCacheListener;



    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When /hardware/{hardwareId} is called with the id from the Hardware
     * Then a 200 status code is returned
     * And the Hardware is returned
     * And the Hardware id matches the one in the database.
     */
    @Test
    void readHardware_whenGivenAValidHardwareId_shouldReturnTheHardware() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardware.setType("light");
        hardware.setName("Test Hardware");
        hardware.setPort("1");
        Map<String, String> configuration = Map.of("test", "test");
        hardware.setConfiguration(configuration);
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        String jsonResponse = mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Hardware responseHardware = objectMapper.readValue(jsonResponse, Hardware.class);
        assertEquals(createdHardware.getId(), responseHardware.getId());
        assertEquals(createdHardware.getType(), responseHardware.getType());
        assertEquals(createdHardware.getName(), responseHardware.getName());
        assertEquals(createdHardware.getPort(), responseHardware.getPort());
        assertEquals(createdHardware.getConfiguration(), responseHardware.getConfiguration());
    }


    /**
     * Given an id which is not associated with a Hardware
     * When /hardware/{hardwareId} is called with the id
     * Then a 404 status code is returned
     * And the response body has 2 fields:
     * - httpStatus: 404
     * - message: "Hardware not found with id of {hardwareId}"
     */
    @Test
    void readHardware_whenGivenAnInvalidHardwareId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/hardware/123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Hardware not found with id of 123"));
    }

    /**
     * Given an id which is associated with a Hardware which was created with a HardwareController via /hardwarecontroller/
     * When a DELETE request is made to /hardware/{hardwareId}
     * Then a 204 status code is returned
     * And the Hardware is deleted from the database
     */
    @Test
    public void deleteHardware_WhenGivenAValidHardwareId_shouldDeleteTheHardware() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);


        mockMvc.perform(delete("/hardware/" + createdHardware.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isNotFound());
    }



    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId} with a Hardware object and the following fields modified:
     * - type
     * - name
     * - port
     * Then a 200 status code is returned
     * And the Hardware is updated in the database
     * And the Hardware has the new values
     */
    @Test
    public void updateHardware_WhenGivenAValidHardwareId_shouldUpdateTheHardware() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        //retrieve the hardware controller from the db

        Hardware updatedHardware = new Hardware();
        updatedHardware.setType("heater");
        updatedHardware.setName("Updated Name");
        updatedHardware.setPort("2");
        String updatedHardwareJson = objectMapper.writeValueAsString(updatedHardware);

        mockMvc.perform(put("/hardware/" + createdHardware.getId())
                        .content(updatedHardwareJson)
                        .contentType("application/json")
                        .content(updatedHardwareJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(updatedHardware.getType()))
                .andExpect(jsonPath("$.name").value(updatedHardware.getName()))
                .andExpect(jsonPath("$.port").value(updatedHardware.getPort()));

        HardwareEntity hardwareEntity = hardwareRepository.findAll().get(0);
        assertEquals(updatedHardware.getType(), hardwareEntity.getHardwareCategory());
        assertEquals(updatedHardware.getName(), hardwareEntity.getName());
        assertEquals(updatedHardware.getPort(), hardwareEntity.getPort());
    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId} with a Hardware object and the desired state has been modified
     * Then a 200 status code is returned
     * And the Hardware is updated in the database
     * And the Hardware has the new desired state
     * And a StateChange message is sent to the MQTT broker
     */
    @Test
    public void updateHardware_WhenGivenAValidHardwareId_shouldUpdateTheHardwareAndSendAChangeStateMessage() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        HardwareState updatedState = createdHardware.getDesiredState();

        createdHardware.getDesiredState().setLevel(10);
        createdHardware.getDesiredState().setState("on");


        String updatedHardwareJson = objectMapper.writeValueAsString(createdHardware);

        mockMvc.perform(put("/hardware/" + createdHardware.getId())
                        .content(updatedHardwareJson)
                        .contentType("application/json")
                        .content(updatedHardwareJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desiredState.id").value(createdHardware.getDesiredState().getId()))
                .andExpect(jsonPath("$.desiredState.level").value(updatedState.getLevel()))
                .andExpect(jsonPath("$.desiredState.state").value(updatedState.getState().toString()));

        boolean asserted = false;
        long startTime = System.currentTimeMillis();

        while (!asserted && System.currentTimeMillis() - startTime < 2000) {
            if (this.mqttCacheListener.getCache("StateChange").size() >= 1) {
                asserted = true;
            }
        }
        List<JsonRpcMessage> results = this.mqttCacheListener.getCache("StateChange");
        assertEquals(1, results.size());
        JsonRpcMessage message = results.get(0);
        HardwareState desiredState = objectMapper.convertValue(message.getParams().get("desiredState"), HardwareState.class);
        assertEquals(updatedState.getLevel(), desiredState.getLevel());
        assertEquals(updatedState.getState(), desiredState.getState());
    }



    /**
     * Given an id which is not associated with a Hardware
     * When a PUT request is made to /hardware/{hardwareId} with a Hardware object
     * Then a 404 status code is returned
     */
    @Test
    public void updateHardware_WhenGivenAnInvalidHardwareId_shouldReturn404() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setType("light");
        hardware.setName("Test Hardware");
        hardware.setPort("1");
        hardware.setId(123456789L);
        String hardwareJson = objectMapper.writeValueAsString(hardware);

        mockMvc.perform(put("/hardware/123456789")
                        .content(hardwareJson)
                        .contentType("application/json")
                        .content(hardwareJson))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a POST request is made to /hardware/{hardwareId}/timer with a Timer object
     * Then a 201 status code is returned
     * And the Timer is returned in the response body
     * And the Timer is created in the database
     */
    @Test
    public void createTimer_WhenGivenAValidHardwareId_shouldCreateTheTimer() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardwareAndTimer();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        Timer createdTimer = createdHardware.getTimers().get(0);

        HardwareEntity hardwareEntity = hardwareRepository.findAll().get(0);
        assertEquals(1, hardwareEntity.getTimers().size());
        TimerEntity timerEntity = hardwareEntity.getTimers().get(0);
        assertEquals(createdTimer.getId(), timerEntity.getId());

    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId} with the same Hardware object but with an additional Timer
     * Then a 200 status code is returned
     * And the hardware now has the additional timer in the response body
     * and the timer can be retrieved from /hardware/{hardwareId}/timer/{timerId}
     */
    @Test
    public void createTimer_WhenGivenAValidHardwareId_shouldCreateTheTimerAndReturnTheTimer() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);


        Timer timer = new Timer();
        timer.setLevel(100);
        timer.setCronString("0 0 0 1 1 ? 2099");
        timer.setSkipNext(true);
        createdHardware.getTimers().add(timer);
        String hardwareJson = objectMapper.writeValueAsString(createdHardware);

        String responseJson = mockMvc.perform(put("/hardware/" + createdHardware.getId())
                        .content(hardwareJson)
                        .contentType("application/json")
                        .content(hardwareJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Hardware updatedHardware = objectMapper.readValue(responseJson, Hardware.class);
        assertEquals(1, updatedHardware.getTimers().size());
        Timer addedTimer = updatedHardware.getTimers().get(0);
        assertEquals(timer.getCronString(), addedTimer.getCronString());
        assertEquals(timer.getLevel(), addedTimer.getLevel());
        assertEquals(timer.getState(), addedTimer.getState());
    }


    /**
     * Given a hardware has been created as part of a hardware controller via /hardwarecontroller/
     * And a timer has been created for the hardware via /hardware/{hardwareId}/timer with:
     * - port = 1
     * And the timer has the on cron string "0/1 * * * * ?"
     * And the timer has the off cron string "0/2 * * * * ?"
     * Then after 2 seconds there should be at least 2 "on" states sent to the client over mqtt
     * and at least 1 "off" state sent to the client over mqtt
     */
    @Test
    public void createTimer_when2SecondsHavePassed_2OnEventsShouldHaveBeenSent_and1OffEventShouldHaveBeenSent() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        Timer timer1 = new Timer();
        timer1.setLevel(100);
        timer1.setCronString("0/3 * * * * ?");
        hardware.getTimers().add(timer1);
        Timer timer2 = new Timer();
        timer2.setLevel(0);
        timer2.setState("off");
        timer2.setCronString("0/2 * * * * ?");
        hardware.getTimers().add(timer2);
        HardwareController hardwareController = this.hardwareControllerTestService.createMqttHardwareControllerWithHardware(List.of(hardware));
        hardware = hardwareController.getHardware().get(0);

        await().atMost(10000, TimeUnit.SECONDS).untilAsserted(() -> {
            if (this.mqttCacheListener.getCache("StateChange").size() >= 3) {
                List<JsonRpcMessage> deliveredStates = this.mqttCacheListener.getCache("StateChange");
                int onCount = 0;
                int offCount = 0;
                for (JsonRpcMessage deliveredState : deliveredStates) {
                    HardwareState state = objectMapper.convertValue(deliveredState.getParams().get("desiredState"), HardwareState.class);
                    if (state.getState().equals("on")) {
                        onCount++;
                    } else if (state.getState().equals("off")) {
                        offCount++;
                    }
                }
                assertTrue(onCount >= 2 && onCount <= 4);
                assertTrue(offCount >= 1 && offCount <= 4);
            }
        });
    }

    /**
     * Given a hardware has been created as part of a hardware controller via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId}/currentstate with a HardwareState object
     * Then a 200 status code is returned
     * And the state has been updated accordingly
     * And a call to /hardware/{hardwareId}/ should return the hardware with the updated state
     */
    @Test
    public void updateCurrentHardwareState_whenGivenAValidHardwareId_shouldUpdateTheState() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        HardwareState hardwareState = new HardwareState();
        hardwareState.setState("on");
        hardwareState.setLevel(10);
        String hardwareStateJson = objectMapper.writeValueAsString(hardwareState);

        mockMvc.perform(put("/hardware/" + createdHardware.getId() + "/currentstate")
                        .content(hardwareStateJson)
                        .contentType("application/json")
                        .content(hardwareStateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(hardwareState.getState().toString()))
                .andExpect(jsonPath("$.level").value(hardwareState.getLevel()));

        mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentState.state").value(hardwareState.getState().toString()))
                .andExpect(jsonPath("$.currentState.level").value(hardwareState.getLevel()));
    }

    /**
     * Given a hardware has been created as part of a hardware controller via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId}/desiredstate with a HardwareState object
     * Then a 200 status code is returned
     * And the state has been updated accordingly
     * And a call to /hardware/{hardwareId}/ should return the hardware with the updated state as desiredState
     */
    @Test
    public void updateDesiredHardwareState_whenGivenAValidHardwareId_shouldUpdateTheState() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        HardwareState hardwareState = new HardwareState();
        hardwareState.setState("on");
        String hardwareStateJson = objectMapper.writeValueAsString(hardwareState);

        mockMvc.perform(put("/hardware/" + createdHardware.getId() + "/desiredstate")
                        .content(hardwareStateJson)
                        .contentType("application/json")
                        .content(hardwareStateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(hardwareState.getState().toString()))
                .andExpect(jsonPath("$.level").value(hardwareState.getLevel()));

        mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.desiredState.state").value(hardwareState.getState().toString()));
    }
}
