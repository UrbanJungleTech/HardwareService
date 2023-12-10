package urbanjungletech.hardwareservice.endpoint;

import com.azure.security.keyvault.secrets.SecretClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.BasicDatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.repository.ScheduledSensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorRepository;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.SensorTestService;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "mqtt.client.enabled=false", "mqtt.server.enabled=false"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SensorEndpointIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SensorReadingRepository sensorReadingRepository;
    @Autowired
    private SensorTestService sensorTestService;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    ScheduledSensorReadingRepository scheduledSensorReadingRepository;
    @Autowired
    SecretClient secretClient;
    @Autowired
    HardwareControllerTestService hardwareControllerTestService;



    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a GET request is made to /sensor/{sensorId}
     * Then a 200 status code is returned
     * And the sensor is returned
     * And the sensor has a name
     * And the sensor has a type
     * And the sensor has a parent
     */
    @Test
    void getSensor_whenGivenAValidSensorId_shouldReturnTheSensor() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();

        Sensor retrievedSensor = hardwareController.getSensors().get(0);

        mockMvc.perform(get("/sensor/" + retrievedSensor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(retrievedSensor.getId()))
                .andExpect(jsonPath("$.sensorType").value(retrievedSensor.getSensorType()));
    }



    /**
     * Given an id which is not associated with a sensor
     * When a GET request is made to /sensor/{sensorId}
     * Then a 404 status code is returned
     * And the response body has 2 fields:
     * - httpStatus: 404
     * - message: "Sensor not found with id of {hardwareId}"
     */
    @Test
    void getSensor_whenGivenAnInvalidSensorId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/sensor/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Sensor not found with id of 1"));
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then a 200 status code is returned
     * And a SensorReading is returned
     * And the SensorReading has a value of 1 for "reading"
     * And the SensorReading has a value of the sensor id for "sensorId"
     * And the SensorReading has no id.
     */
    @Test
    void getSensorReading_whenGivenAValidSensorId_shouldReturnTheSensorReading() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        mockMvc.perform(get("/sensor/" + createdSensor.getId() + "/reading"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reading").value(1))
                .andExpect(jsonPath("$.sensorId").value(createdSensor.getId()))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    /**
     * Given an id which is not associated with a sensor
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then a 404 status code is returned
     * And the response body has 2 fields:
     * - httpStatus: 404
     * - message: "Sensor not found with id of {sensorId}"
     */
    @Test
    void getSensorReading_whenGivenAnInvalidSensorId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/sensor/1/reading"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Sensor not found with id of 1"));
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a PUT request is made to /sensor/{sensorId} with a valid sensor
     * Then a 200 status code is returned
     * And the sensor is updated
     * And the sensor has a new name matching the name in the request
     * And the sensor has a new type matching the type in the request
     * And the sensor has a new port matching the port in the request
     */
    @Test
    void updateSensor_whenGivenAValidSensor_shouldUpdateTheSensor() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor createdSensor = hardwareController.getSensors().get(0);
        Sensor updatedSensor = new Sensor();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("test", "test value");
        updatedSensor.setMetadata(metadata);
        Map<String, String> configuration = new HashMap<>();
        configuration.put("test", "test config value");
        updatedSensor.setConfiguration(configuration);
        updatedSensor.setName("Updated Sensor");
        updatedSensor.setSensorType("humidity");
        updatedSensor.setPort("2");

        String updatedSensorJson = objectMapper.writeValueAsString(updatedSensor);
        String responseString = mockMvc.perform(put("/sensor/" + createdSensor.getId())
                        .content(updatedSensorJson)
                        .contentType("application/json")
                        .content(updatedSensorJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Sensor responseSensor = objectMapper.readValue(responseString, Sensor.class);
        assertEquals(updatedSensor.getName(), responseSensor.getName());
        assertEquals(updatedSensor.getSensorType(), responseSensor.getSensorType());
        assertEquals(updatedSensor.getPort(), responseSensor.getPort());
        assertEquals(updatedSensor.getMetadata(), responseSensor.getMetadata());
        assertEquals(updatedSensor.getConfiguration(), responseSensor.getConfiguration());
    }

    /**
     * Given an id which is not associated with a sensor
     * When a PUT request is made to /sensor/{sensorId} with a valid sensor
     * Then a 404 status code is returned
     * And the response body has 2 fields:
     * - httpStatus: 404
     * - message: "Sensor not found with id of {sensorId}"
     */
    @Test
    void updateSensor_whenGivenAnInvalidSensorId_shouldReturn404() throws Exception {
        Sensor sensor = new Sensor();
        String sensorJson = objectMapper.writeValueAsString(sensor);
        mockMvc.perform(put("/sensor/1")
                        .content(sensorJson)
                        .contentType("application/json")
                        .content(sensorJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Sensor not found with id of 1"));
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a DELETE request is made to /sensor/{sensorId}
     * Then a 204 status code is returned
     * And the sensor is deleted from the database
     */
    @Test
    void deleteSensor_whenGivenAValidSensorId_shouldDeleteTheSensor() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor createdSensor = hardwareController.getSensors().get(0);
        mockMvc.perform(delete("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Sensor not found with id of " + createdSensor.getId()));
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a DELETE request is made to /sensor/{sensorId}
     * Then a message should be sent to the hardware controller of type DeregisterSensor
     *
     */
    @Test
    void deleteSensor_whenGivenAValidSensorId_shouldSendDeregisterSensorMessage() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor createdSensor = hardwareController.getSensors().get(0);
        mockMvc.perform(delete("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNoContent());

        await()
                .atMost(Duration.of(3, java.time.temporal.ChronoUnit.SECONDS))
                .with()
                .untilAsserted(() -> {
                    String responseJson = mockMvc.perform(get("/hardwarecontroller/" + hardwareController.getId()))
                            .andExpect(status().isOk())
                            .andReturn().getResponse().getContentAsString();
                    HardwareController updatedHardwareController = objectMapper.readValue(responseJson, HardwareController.class);
                    assertEquals(0, updatedHardwareController.getSensors().size());
                });
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a POST request is made to /sensor/{sensorId}/scheduledReading with a valid ScheduledReading
     * Then a 201 status code is returned
     * And the ScheduledReading is created
     * And the ScheduledReading has an id
     */
    @Test
    void createScheduledReading_whenGivenAValidScheduledReading_shouldCreateTheScheduledReading() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.getConfiguration().put("serialNumber", "1234");
        Sensor sensor = new Sensor();
        sensor.setSensorType("temperature");
        sensor.setName("Test Sensor");
        sensor.setPort("1");
        hardwareController.getSensors().add(sensor);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0 0 0 1 1 ? 2099");
        scheduledReading.setSensorId(createdSensor.getId());
        String scheduledReadingJson = objectMapper.writeValueAsString(scheduledReading);
        mockMvc.perform(post("/sensor/" + createdSensor.getId() + "/scheduledreading")
                        .content(scheduledReadingJson)
                        .contentType("application/json")
                        .content(scheduledReadingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    /**
     * Given an id which is not associated with a sensor
     * When a POST request is made to /sensor/{sensorId}/scheduledReading with a valid ScheduledReading
     * Then a 404 status code is returned
     * And the response body has 2 fields:
     * - httpStatus: 404
     * - message: "Sensor not found with id of {sensorId}"
     */
    @Test
    void createScheduledReading_whenGivenAnInvalidSensorId_shouldReturn404() throws Exception {
        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        String scheduledReadingJson = objectMapper.writeValueAsString(scheduledReading);
        mockMvc.perform(post("/sensor/1/scheduledreading")
                        .content(scheduledReadingJson)
                        .contentType("application/json")
                        .content(scheduledReadingJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Sensor not found with id of 1"));
    }

    /**
     * Given a HardwareController has been created via /hardwarecontroller/ with a sensor
     * Given a ScheduledReading has been created via /sensor/{sensorId}/scheduledReading
     * When a GET request is made to /sensor/{sensorId}/readings
     * Then a 200 status code is returned
     * And the response body is a list of ScheduledReadings
     */
    @Test
    void getScheduledReadings_whenGivenAValidSensorId_shouldReturnAListOfScheduledReadings() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Sensor sensor = new Sensor();
        sensor.setPort("1");
        sensor.setSensorType("temperature");
        sensor.setName("Test Sensor");
        hardwareController.getSensors().add(sensor);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        Sensor createdSensor = createdHardwareController.getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");
        BasicDatabaseSensorReadingRouter router = new BasicDatabaseSensorReadingRouter();
        scheduledReading.getRouters().add(router);

        String scheduledReadingJson = objectMapper.writeValueAsString(scheduledReading);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss").toFormatter();
        String startTime = LocalDateTime.now().format(formatter);
        mockMvc.perform(post("/sensor/" + createdSensor.getId() + "/scheduledreading")
                        .content(scheduledReadingJson)
                        .contentType("application/json")
                        .content(scheduledReadingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cronString").value("0/1 * * * * ?"))
                .andExpect(jsonPath("$.id").exists());
        //give it enough time to get at least one reading

        await().atMost(Duration.of(3, ChronoUnit.SECONDS)).until(
                () -> {

                    String endTime = LocalDateTime.now().format(formatter);
                    String responseJson = mockMvc.perform(get("/sensor/" + createdSensor.getId() + "/readings").queryParam("startDate", startTime).queryParam("endDate", endTime))
                            .andExpect(status().isOk())
                            .andReturn().getResponse().getContentAsString();
                    List<SensorReading> sensorReadings = objectMapper.readValue(responseJson, new TypeReference<List<SensorReading>>() {
                    });
                    return sensorReadings.size() >= 1;
                }

        );
    }
}
