package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.jsonrpc.model.RegisterSensorMessage;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.helpers.services.http.SensorTestService;
import urbanjungletech.hardwareservice.helpers.services.mqtt.MqttTestService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RegisterSensorIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private SensorTestService sensorTestService;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;

    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234"
     * When a json payload of the form:
     * {
     * "jsonrpc": "2.0",
     * "method": "RegisterSensor",
     * "params": {
     * "serialNumber":"1234",
     * "hardware": {
     * "port": 1,
     * "sensorType": "temperature"
     * }
     * }
     * }
     * is delivered to the mqtt topic "FromMicrocontroller"
     * Then a Sensor with the type "temperature" should be created and associated with the HardwareController with the serial number "1234"
     */
    @Test
    public void testRegisterSensor() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor sensor = hardwareController.getSensors().get(0);

        RegisterSensorMessage registerSensorMessage = new RegisterSensorMessage(sensor);
        String registerSensorMessageJson = objectMapper.writeValueAsString(registerSensorMessage);

        this.mqttTestService.sendMessage(registerSensorMessageJson);

        await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    MvcResult hardwareResponse = mockMvc.perform(get("/hardwarecontroller/" + hardwareController.getId() + "/sensor"))
                            .andReturn();
                    if (hardwareResponse.getResponse().getStatus() == HttpStatus.OK.value()) {
                        List<Sensor> responseSensors = objectMapper.readValue(hardwareResponse.getResponse().getContentAsString(), List.class);
                        assertEquals(responseSensors.size(), 1);
                    }
                });
    }
}
