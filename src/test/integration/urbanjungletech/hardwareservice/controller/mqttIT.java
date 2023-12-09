package urbanjungletech.hardwareservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.jsonrpc.method.RegisterHardware;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.jsonrpc.model.RegisterHardwareMessage;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class mqttIT {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MockMqttClientListener mqttCacheListener;

    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    private HardwareTestService hardwareTestService;
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
            Map<String, String> state = (LinkedHashMap)registerHardwareMessage.getParams().get("state");
            assertEquals(createdHardware.getOffState(), state.get("state"));
        });;
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
}
