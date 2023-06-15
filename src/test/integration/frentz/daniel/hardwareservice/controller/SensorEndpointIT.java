package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.client.model.SensorReading;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.entity.SensorReadingEntity;
import frentz.daniel.hardwareservice.repository.SensorReadingRepository;
import frentz.daniel.hardwareservice.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class SensorEndpointIT {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SensorReadingRepository sensorReadingRepository;

    @BeforeEach
    void setUp() {
        this.sensorRepository.deleteAll();
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
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        Sensor sensor = new Sensor();
        sensor.setSensorType("temperature");
        hardwareController.getSensors().add(sensor);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        HardwareController retrievedHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);
        Sensor retrievedSensor = retrievedHardwareController.getSensors().get(0);

        mockMvc.perform(get("/sensor/" + createdHardwareController.getSensors().get(0).getId()))
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
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("1234");
        hardwareController.setName("Test Hardware Controller");
        Sensor sensor = new Sensor();
        sensor.setSensorType("temperature");
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
        mockMvc.perform(get("/sensor/" + createdHardwareController.getSensors().get(0).getId() + "/reading"))
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
        mockMvc.perform(delete("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/sensor/" + createdSensor.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Sensor not found with id of " + createdSensor.getId()));
    }
}
