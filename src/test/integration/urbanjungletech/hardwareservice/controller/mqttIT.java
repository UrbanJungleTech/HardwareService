package urbanjungletech.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.*;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.http.SensorTestService;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class mqttIT {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MockMqttClientListener mqttCacheListener;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    private HardwareTestService hardwareTestService;
    @Autowired
    private SensorTestService sensorTestService;

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * Then a RegisterHardware message is sent to the MQTT broker
     */
    @Test
    void readHardware_whenGivenAValidHardwareId_shouldSendARegisterHardwareMessage() throws Exception {

        HardwareController hardwareController = this.hardwareControllerTestService.createHardwareMqttController();
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardware.setOffState("off");
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        await().untilAsserted(() -> {
            assertEquals(1, this.mqttCacheListener.getCache("RegisterHardware").size());
            JsonRpcMessage registerHardwareMessage = this.mqttCacheListener.getCache("RegisterHardware").get(0);
            assertEquals(createdHardware.getPort(), registerHardwareMessage.getParams().get("port"));
            Map<String, String> state = (LinkedHashMap) registerHardwareMessage.getParams().get("state");
            assertEquals(createdHardware.getOffState(), state.get("state"));
        });
        ;
    }

    /**
     * Given an id which is associated with a Hardware which was created with a HardwareController via /hardwarecontroller/
     * When a DELETE request is made to /hardware/{hardwareId}
     * Then a DeregisterHardware message is sent to the MQTT broker
     */
    @Test
    public void deleteHardware_WhenGivenAValidHardwareId_shouldSendADeregisterHardwareMessage() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        mockMvc.perform(delete("/hardware/" + createdHardware.getId()))
                .andExpect(status().isNoContent());

        await().untilAsserted(() -> {
            if (this.mqttCacheListener.getCache("DeregisterHardware").size() >= 1) {
                JsonRpcMessage message = mqttCacheListener.getCache("DeregisterHardware").get(0);
                assertEquals(createdHardware.getPort(), message.getParams().get("port"));
            }
        });
    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId} with a Hardware object and the desired state has not been modified
     * Then a message is not sent to the MQTT broker of method StateChange
     */
    @Test
    public void updateHardware_WhenGivenAValidHardwareId_shouldUpdateTheHardwareButNotTheDesiredStateAndNotSendAChangeStateMessage() throws Exception {
        //publishing the hardware controller should not result in a state change
        this.hardwareTestService.createMqttHardwareControllerWithDefaultHardware();
        boolean asserted = false;
        long startTime = System.currentTimeMillis();
        while (!asserted && System.currentTimeMillis() - startTime < 2000) {
            if (this.mqttCacheListener.getCache("StateChange").size() >= 1) {
                asserted = true;
            }
        }
        List<JsonRpcMessage> results = this.mqttCacheListener.getCache("StateChange");
        assertEquals(0, results.size());
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
        hardware.setOffState("off");
        hardware.setPort("1");
        Timer timer1 = new Timer();
        timer1.setLevel(100);
        timer1.setState("on");
        timer1.setCronString("0/3 * * * * ?");
        hardware.getTimers().add(timer1);
        Timer timer2 = new Timer();
        timer2.setLevel(0);
        timer2.setState("off");
        timer2.setCronString("0/2 * * * * ?");
        hardware.getTimers().add(timer2);
        HardwareController hardwareController = this.hardwareControllerTestService.createMqttHardwareController(List.of(hardware));
        hardware = hardwareController.getHardware().get(0);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertTrue(this.mqttCacheListener.getCache("StateChange").size() >= 3);
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
        });
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

        String responseJson = mockMvc.perform(put("/hardware/" + createdHardware.getId())
                        .content(updatedHardwareJson)
                        .contentType("application/json")
                        .content(updatedHardwareJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Hardware updatedHardware = objectMapper.readValue(responseJson, Hardware.class);
        HardwareState updatedHardwareDesiredState = updatedHardware.getDesiredState();
        assertEquals(updatedState.getLevel(), updatedHardwareDesiredState.getLevel());
        assertEquals(updatedState.getState(), updatedHardwareDesiredState.getState());
        assertNotNull(updatedHardwareDesiredState.getId());


        await().untilAsserted(() -> {
            assertTrue(this.mqttCacheListener.getCache("StateChange").size() >= 1);
            JsonRpcMessage message = mqttCacheListener.getCache("StateChange").get(0);
            HardwareState desiredState = objectMapper.convertValue(message.getParams().get("desiredState"), HardwareState.class);
            assertEquals(updatedState.getLevel(), desiredState.getLevel());
            assertEquals(updatedState.getState(), desiredState.getState());
        });
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * A RegisterSensor message should have been sent to the microcontroller via mqtt
     */
    @Test
    void getSensor_whenGivenAValidSensorId_shouldSendARegisterSensorMessage() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();

        Sensor createdSensor = hardwareController.getSensors().get(0);

        await()
                .atMost(Duration.of(3, ChronoUnit.SECONDS))
                .until(() -> this.mqttCacheListener.getCache("RegisterSensor", Map.of("port", (String)createdSensor.getPort())).size() >= 1);

        List<JsonRpcMessage> results = this.mqttCacheListener.getCache("RegisterSensor", Map.of("port", (String)createdSensor.getPort()));
        assertEquals(1, results.size());
        JsonRpcMessage message = results.get(0);
        assertEquals(createdSensor.getPort(), (String)message.getParams().get("port"));
    }

}
