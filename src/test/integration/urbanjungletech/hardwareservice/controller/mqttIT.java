package urbanjungletech.hardwareservice.controller;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;

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
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        //HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        await().untilAsserted(() -> {
            assertEquals(1, this.mqttCacheListener.getCache("RegisterHardware").size());
            JsonRpcMessage registerHardwareMessage = this.mqttCacheListener.getCache("RegisterHardware").get(0);
            assertEquals(createdHardware.getPort(), registerHardwareMessage.getParams().get("port"));
        });;
    }

    /**
     * Given an id which is associated with a Hardware which was created with a HardwareController via /hardwarecontroller/
     * When a DELETE request is made to /hardware/{hardwareId}
     * Then a DeregisterHardware message is sent to the MQTT broker
     */
    @Test
    public void deleteHardware_WhenGivenAValidHardwareId_shouldSendADeregisterHardwareMessage() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
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
}
