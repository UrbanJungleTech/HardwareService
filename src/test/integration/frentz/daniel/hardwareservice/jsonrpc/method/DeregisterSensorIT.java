package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.MqttTestService;
import frentz.daniel.hardwareservice.SensorTestService;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class DeregisterSensorIT {

    @Autowired
    private SensorTestService sensorTestService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private MockMvc mockMvc;

    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234" with a sensor with the port 1
     * When a json payload of the form:
     * {
     *   "jsonrpc": "2.0",
     *   "method": "DeregisterHardware",
     *   "params": {
     *   "serialNumber":"1234",
     *   "port": 1
     *   }
     * }
     * is delivered to the mqtt topic "FromMicrocontroller"
     * Then the Sensor should be deleted.
     */
    @Test
    public void testDeregisterSensor() throws Exception{
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();

        Map<String, Object> params = new HashMap<>();
        params.put("serialNumber", hardwareController.getSerialNumber());
        params.put("port", hardwareController.getSensors().get(0).getPort());
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("DeregisterSensor", params);

        String rpcMessage = objectMapper.writeValueAsString(jsonRpcMessage);

        this.mqttTestService.sendMessage(rpcMessage);

        boolean isSensorDeleted = false;
        long timeoutMillis = 3000;
        long sleepMillis = 500;
        long timeWaitedMillis = 0;
        while (!isSensorDeleted && timeWaitedMillis < timeoutMillis) {
            Thread.sleep(sleepMillis);
            timeWaitedMillis += sleepMillis;
            MvcResult hardwareResponse = mockMvc.perform(get("/hardwarecontroller/" + hardwareController.getId() + "/sensor"))
                    .andReturn();
            if (hardwareResponse.getResponse().getStatus() == HttpStatus.OK.value()) {
                List<Sensor> sensorList = objectMapper.readValue(hardwareResponse.getResponse().getContentAsString(), List.class);
                isSensorDeleted = sensorList.isEmpty();
            }
        }
        assertTrue(isSensorDeleted);
    }
}
