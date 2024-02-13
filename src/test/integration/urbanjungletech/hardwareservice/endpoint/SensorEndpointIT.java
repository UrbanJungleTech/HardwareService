package urbanjungletech.hardwareservice.endpoint;

import com.azure.security.keyvault.secrets.SecretClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
import urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller.MockHardwareController;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensor;
import urbanjungletech.hardwareservice.helpers.mock.sensorreadingrouter.MockSensorReadingRouter;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.helpers.services.http.SensorTestService;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.repository.ScheduledSensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "development.mqtt.client.enabled=false",
        "mqtt.server.enabled=false",
        "mqtt-rpc.enabled=false"})
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
    @Autowired
    private HardwareTestService hardwareTestService;
    @Autowired
    private SensorTestService sensorService;

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
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);


        Sensor retrievedSensor = createdHardwareController.getSensors().get(0);

        String retrievedSensorJson = mockMvc.perform(get("/sensor/" + retrievedSensor.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Sensor responseSensor = this.objectMapper.readValue(retrievedSensorJson, Sensor.class);
        assertEquals(retrievedSensor.getId(), responseSensor.getId());
        assertEquals(retrievedSensor.getName(), responseSensor.getName());
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
     * Given a sensor is created
     * When a POST request is made to /sensor/{sensorId}/reading with a valid SensorReading
     * Then a 201 status code is returned
     * And the SensorReading is returned as the body
     */
    @Test
    void createSensorReading_whenGivenAValidSensorReading_shouldCreateTheSensorReading() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        SensorReading sensorReading = new SensorReading();
        sensorReading.setReading(1);
        sensorReading.setSensorId(createdSensor.getId());
        String sensorReadingJson = objectMapper.writeValueAsString(sensorReading);
        String responseString = mockMvc.perform(post("/sensor/" + createdSensor.getId() + "/reading")
                        .content(sensorReadingJson)
                        .contentType("application/json")
                        .content(sensorReadingJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        SensorReading responseSensorReading = objectMapper.readValue(responseString, SensorReading.class);
        assertEquals(sensorReading.getReading(), responseSensorReading.getReading());
        assertEquals(sensorReading.getSensorId(), responseSensorReading.getSensorId());
        assertNotNull(responseSensorReading.getId());
    }

    /**
     * Given a valid SensorReading
     * And given an alert is created with a condition with a threshold of
     * When a POST request is made to /sensor/{sensorId}/reading
     */

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
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        Sensor updatedSensor = new MockSensor();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("test", "test value");
        Map<String, String> configuration = new HashMap<>();
        configuration.put("test", "test config value");
        updatedSensor.setName("Updated Sensor");
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
        assertEquals(updatedSensor.getPort(), responseSensor.getPort());
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
        Sensor sensor = new MockSensor();
        String sensorJson = objectMapper.writeValueAsString(sensor);
        String responseJson = mockMvc.perform(put("/sensor/1")
                        .content(sensorJson)
                        .contentType("application/json")
                        .content(sensorJson))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        NotFoundException notFoundException = this.objectMapper.readValue(responseJson, NotFoundException.class);
        assertEquals(notFoundException.getMessage(), "Sensor not found with id of 1");
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a DELETE request is made to /sensor/{sensorId}
     * Then a 204 status code is returned
     * And the sensor is deleted from the database
     */
    @Test
    void deleteSensor_whenGivenAValidSensorId_shouldDeleteTheSensor() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        mockMvc.perform(delete("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNoContent());
        String exceptionJson = mockMvc.perform(get("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        NotFoundException notFoundException = this.objectMapper.readValue(exceptionJson, NotFoundException.class);
        assertEquals("Sensor not found with id of " + createdSensor.getId(), notFoundException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), notFoundException.getHttpStatus());
    }

    /**
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * When a DELETE request is made to /sensor/{sensorId}
     * Then a message should be sent to the hardware controller of type DeregisterSensor
     *
     */
    @Test
    void deleteSensor_whenGivenAValidSensorId_shouldSendDeregisterSensorMessage() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        mockMvc.perform(delete("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNoContent());

        await()
                .atMost(Duration.of(3, java.time.temporal.ChronoUnit.SECONDS))
                .with()
                .untilAsserted(() -> {
                    String responseJson = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId()))
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
        HardwareController hardwareController = new MockHardwareController();
        Sensor sensor = new MockSensor();
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
        Sensor sensor = new MockSensor();
        sensor.setPort("1");
        sensor.setName("Test Sensor");
        hardwareController.getSensors().add(sensor);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        Sensor createdSensor = createdHardwareController.getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");

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

    /**
     * Given a valid sensor with a sensor reading router without an id
     * When a POST request is made to /sensor/
     * Then a 201 status code is returned
     * And the sensor is created
     * And the sensor has a sensor reading router with an id
     */
    @Test
    void createSensor_whenGivenASensorWithASensorReadingRouterWithoutAnId_shouldCreateTheSensor() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        MockSensorReadingRouter sensorReadingRouter = new MockSensorReadingRouter();
        sensor.getSensorReadingRouters().add(sensorReadingRouter);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        assertEquals(1, createdSensor.getSensorReadingRouters().size());
        assertNotNull(createdSensor.getSensorReadingRouters().get(0).getId());
        assertSame(MockSensorReadingRouter.class, createdSensor.getSensorReadingRouters().get(0).getClass());
    }

    /**
     * Given a sensor reading router has been created
     * and a sensor object with the created sensor reading router in the sensors list of routers
     * When a POST request is made to /hardwarecontroller/ to create the sensor as part of the hardwarecontroller
     * Then a 201 status code is returned
     * And the sensor is created
     * And the sensor has the same router in the list of routers
     */
    @Test
    void createSensor_whenGivenASensorWithASensorReadingRouterWithAnId_shouldCreateTheSensor() throws Exception {
        SensorReadingRouter sensorReadingRouter = new MockSensorReadingRouter();
        String sensorReadingRouterJson = this.mockMvc.perform(post("/sensorreadingrouter/")
                        .content(this.objectMapper.writeValueAsString(sensorReadingRouter))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        SensorReadingRouter createdSensorReadingRouter = this.objectMapper.readValue(sensorReadingRouterJson, SensorReadingRouter.class);

        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        sensor.getSensorReadingRouters().add(createdSensorReadingRouter);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        assertEquals(1, createdSensor.getSensorReadingRouters().size());
        assertEquals(createdSensorReadingRouter.getId(), createdSensor.getSensorReadingRouters().get(0).getId());
    }

    /**
     * Given a sensor object with a created sensor reading router and a new sensor reading router
     * When a POST request is made to /hardwarecontroller/ to create the sensor as part of the hardwarecontroller
     * Then a 201 status code is returned
     * And the sensor is created
     * And the sensor has the same routers in the list of routers, with the new one having a new id and the existing one
     * having the same id
     */
    @Test
    void createSensor_whenGivenASensorWithASensorReadingRouterWithAnIdAndANewSensorReadingRouter_shouldCreateTheSensor() throws Exception {
        SensorReadingRouter sensorReadingRouter = new MockSensorReadingRouter();
        String sensorReadingRouterJson = this.mockMvc.perform(post("/sensorreadingrouter/")
                        .content(this.objectMapper.writeValueAsString(sensorReadingRouter))
                        .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        SensorReadingRouter createdSensorReadingRouter = this.objectMapper.readValue(sensorReadingRouterJson, SensorReadingRouter.class);

        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        sensor.getSensorReadingRouters().add(createdSensorReadingRouter);
        SensorReadingRouter newSensorReadingRouter = new MockSensorReadingRouter();
        sensor.getSensorReadingRouters().add(newSensorReadingRouter);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor createdSensor = createdHardwareController.getSensors().get(0);
        assertEquals(2, createdSensor.getSensorReadingRouters().size());
        assertEquals(createdSensorReadingRouter.getId(), createdSensor.getSensorReadingRouters().get(0).getId());
        assertNotNull(createdSensor.getSensorReadingRouters().get(1).getId());
    }
}
