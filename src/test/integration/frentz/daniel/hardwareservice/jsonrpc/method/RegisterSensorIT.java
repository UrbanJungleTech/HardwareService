package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.HardwareControllerTestService;
import frentz.daniel.hardwareservice.MqttTestService;
import frentz.daniel.hardwareservice.SensorTestService;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterSensorIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    private SensorTestService sensorTestService;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;
    @AfterEach
    public void tearDown() throws Exception {
        this.hardwareControllerRepository.deleteAll();
    }

    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234"
     * When a json payload of the form:
     * {
     *    "jsonrpc": "2.0",
     *    "method": "RegisterSensor",
     *    "params": {
     *    "serialNumber":"1234",
     *    "hardware": {
     *    "port": 1,
     *    "sensorType": "temperature"
     *    }
     *    }
     * }
     * is delivered to the mqtt topic "FromMicrocontroller"
     * Then a Sensor with the type "temperature" should be created and associated with the HardwareController with the serial number "1234"
     */
//    @Test
    public void testRegisterSensor() throws Exception{
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        String sensorJson = objectMapper.writeValueAsString(sensor);


        this.mqttTestService.sendMessage("{\"serialNumber\": \"1234\", \"sensor\": " + sensorJson + "}");

        boolean isSensorCreated = false;
        long timeoutMillis = 3000;
        long sleepMillis = 500;
        long timeWaitedMillis = 0;
        while (!isSensorCreated && timeWaitedMillis < timeoutMillis) {
            Thread.sleep(sleepMillis);
            timeWaitedMillis += sleepMillis;
            MvcResult hardwareResponse = mockMvc.perform(get("/hardwarecontroller/" + hardwareController.getId() + "/sensor"))
                    .andReturn();
            if (hardwareResponse.getResponse().getStatus() == HttpStatus.OK.value()) {
                List<Sensor> sensorList = objectMapper.readValue(hardwareResponse.getResponse().getContentAsString(), List.class);
                isSensorCreated = !sensorList.isEmpty();
            }
        }
    }
}
