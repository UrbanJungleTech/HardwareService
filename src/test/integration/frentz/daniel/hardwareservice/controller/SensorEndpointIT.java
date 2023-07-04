package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.SensorTestService;
import frentz.daniel.hardwareservice.config.mqtt.mockclient.MockMqttClientListener;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.model.SensorReading;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.ScheduledSensorReadingRepository;
import frentz.daniel.hardwareservice.repository.SensorReadingRepository;
import frentz.daniel.hardwareservice.repository.SensorRepository;
import frentz.daniel.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;
import frentz.daniel.hardwareservice.schedule.sensor.SensorScheduleService;
import io.moquette.broker.Server;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SensorEndpointIT {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SensorReadingRepository sensorReadingRepository;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;
    @Autowired
    private ScheduledSensorReadingRepository scheduledSensorReadingRepository;
    @Autowired
    IMqttClient mqttClient;
    @Autowired
    private SensorTestService sensorTestService;
    @Autowired
    MockMqttClientListener mockMqttClientListener;
    @Autowired
    Server mqttBroker;
    @Autowired
    private SensorScheduleService sensorScheduleService;
    @Autowired
    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    @BeforeEach
    void setUp() throws SchedulerException {
        this.hardwareControllerRepository.deleteAll();
        this.mockMqttClientListener.clear();
        this.sensorScheduleService.deleteAll();
        this.scheduledHardwareScheduleService.deleteAllSchedules();
    }

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
     * Given a HardwareController with a sensor has been created via /hardwarecontroller/
     * A RegisterSensor message should have been sent to the microcontroller via mqtt
     */
    @Test
    void getSensor_whenGivenAValidSensorId_shouldSendARegisterSensorMessage() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();

        Sensor createdSensor = hardwareController.getSensors().get(0);

        boolean asserted = false;
        long startTime = System.currentTimeMillis();
        int port = (int)createdSensor.getPort();
        while (!asserted && System.currentTimeMillis() - startTime < 10000) {
            if (this.mockMqttClientListener.getCache("RegisterSensor", Map.of("port", (int)createdSensor.getPort())).size() >= 1) {
                asserted = true;
                Thread.sleep(10);
            }
        }
        List<JsonRpcMessage> results = this.mockMqttClientListener.getCache("RegisterSensor", Map.of("port", (int)createdSensor.getPort()));
        assertEquals(1, results.size());
        JsonRpcMessage message = results.get(0);
        assertEquals(createdSensor.getPort(), Long.valueOf((int)message.getParams().get("port")));
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
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor createdSensor = hardwareController.getSensors().get(0);
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
        updatedSensor.setName("Updated Sensor");
        updatedSensor.setSensorType("humidity");
        updatedSensor.setPort(2);
        String updatedSensorJson = objectMapper.writeValueAsString(updatedSensor);
        mockMvc.perform(put("/sensor/" + createdSensor.getId())
                        .content(updatedSensorJson)
                        .contentType("application/json")
                        .content(updatedSensorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedSensor.getName()))
                .andExpect(jsonPath("$.sensorType").value(updatedSensor.getSensorType()))
                .andExpect(jsonPath("$.port").value(updatedSensor.getPort()));
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
        boolean asserted = false;
        long startTime = System.currentTimeMillis();

        while (!asserted && System.currentTimeMillis() - startTime < 1000) {
            if (this.mockMqttClientListener.getCache("DeregisterSensor").size() > 0) {
                asserted = true;
                Thread.sleep(100);
            }
        }
        JsonRpcMessage rpcMessage = this.mockMqttClientListener.getCache("DeregisterSensor").get(0);
        assertEquals(createdSensor.getPort(), Long.valueOf((int)rpcMessage.getParams().get("port")));
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
        hardwareController.setSerialNumber("1234");
        Sensor sensor = new Sensor();
        sensor.setSensorType("temperature");
        sensor.setName("Test Sensor");
        sensor.setPort(1);
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
        mockMvc.perform(post("/sensor/" + createdSensor.getId() + "/scheduledReading/")
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
        mockMvc.perform(post("/sensor/1/scheduledReading/")
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
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor createdSensor = hardwareController.getSensors().get(0);
        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");

        String scheduledReadingJson = objectMapper.writeValueAsString(scheduledReading);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss").toFormatter();
        String startTime = LocalDateTime.now().format(formatter);
        mockMvc.perform(post("/sensor/" + createdSensor.getId() + "/scheduledReading/")
                        .content(scheduledReadingJson)
                        .contentType("application/json")
                        .content(scheduledReadingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cronString").value("0/1 * * * * ?"))
                .andExpect(jsonPath("$.id").exists());
        //give it enough time to get at least one reading
        Thread.sleep(3000);
        String endTime = LocalDateTime.now().format(formatter);
        MvcResult result = mockMvc.perform(get("/sensor/" + createdSensor.getId() + "/readings").queryParam("startDate", startTime).queryParam("endDate", endTime))
                .andExpect(status().isOk())
                .andReturn();
        List<SensorReading> sensorReadings = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SensorReading>>() {
        });
        assertNotEquals(0, sensorReadings.size());
        SensorReading firstReading = sensorReadings.get(0);
        assertEquals(1, firstReading.getReading());
    }

}
