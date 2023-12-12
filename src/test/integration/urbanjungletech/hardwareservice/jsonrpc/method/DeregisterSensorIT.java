package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.mqtt.MqttTestService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeregisterSensorIT {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    HardwareControllerTestService hardwareControllerTestService;


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
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        hardwareController.getConfiguration().put("serialNumber", "1234");
        Sensor sensor = new Sensor();
        sensor.setPort("1");
        hardwareController.getSensors().add(sensor);
        hardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        long sensorId = hardwareController.getSensors().get(0).getId();

        Map<String, Object> params = new HashMap<>();
        params.put("serialNumber", hardwareController.getConfiguration().get("serialNumber"));
        params.put("port", hardwareController.getSensors().get(0).getPort());
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage("DeregisterSensor", params);

        String rpcMessage = objectMapper.writeValueAsString(jsonRpcMessage);

        this.mqttTestService.sendMessage(rpcMessage);


        await().atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            Sensor responseSensor = this.hardwareControllerTestService.findSensor(sensorId);
            assertNull(responseSensor);
        });
    }
}
