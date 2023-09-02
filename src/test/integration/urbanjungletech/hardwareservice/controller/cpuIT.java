package urbanjungletech.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class cpuIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Given a controller has been created of type cpu with a sensor whose configuration contains
     * a sensorType of TEMPERATURE
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then the response status is 200
     * And the response body is a double
     * And the response body is greater than 0
     */
    @Test
    public void getSensorReading_temperatureSensor_returnsDoubleGreaterThanZero() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("cpu");
        Sensor sensor = new Sensor();
        sensor.setName("Temperature Sensor");
        Map<String, String> configuration = Map.of("sensorType", "TEMPERATURE");
        sensor.setConfiguration(configuration);
        hardwareController.setSensors(List.of(sensor));
        String controllerResponseString = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        HardwareController controllerResponse = objectMapper.readValue(controllerResponseString, HardwareController.class);
        Sensor sensorResponse = controllerResponse.getSensors().get(0);
        String sensorReadingResponse = this.mockMvc.perform(get("/sensor/" + sensorResponse.getId() + "/reading"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReading sensorReading = objectMapper.readValue(sensorReadingResponse, SensorReading.class);
        Double reading = sensorReading.getReading();
        assertNotNull(reading);
    }

    /**
     * Given a controller has been created of type cpu with a sensor whose configuration contains
     * a sensorType of CLOCK_SPEED
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then the response status is 200
     * And the response body is a double
     * And the response body is greater than 0
     */
    @Test
    public void getSensorReading_clockSpeedSensor_returnsDoubleGreaterThanZero() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("cpu");
        Sensor sensor = new Sensor();
        sensor.setName("Clock Speed Sensor");
        Map<String, String> configuration = Map.of("sensorType", "CLOCK_SPEED");
        sensor.setConfiguration(configuration);
        hardwareController.setSensors(List.of(sensor));
        String controllerResponseString = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        HardwareController controllerResponse = objectMapper.readValue(controllerResponseString, HardwareController.class);
        Sensor sensorResponse = controllerResponse.getSensors().get(0);
        String sensorReadingResponse = this.mockMvc.perform(get("/sensor/" + sensorResponse.getId() + "/reading"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReading sensorReading = objectMapper.readValue(sensorReadingResponse, SensorReading.class);
        Double reading = sensorReading.getReading();
        assertNotNull(reading);
    }

    /**
     * Given a controller has been created of type cpu with a sensor whose configuration contains
     * a sensorType which is invalid
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then the response status is 400
     */
    @Test
    public void getSensorReading_invalidSensorType_returns400() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("cpu");
        Sensor sensor = new Sensor();
        sensor.setName("Invalid Sensor");
        Map<String, String> configuration = Map.of("sensorType", "INVALID");
        sensor.setConfiguration(configuration);
        hardwareController.setSensors(List.of(sensor));
        String controllerResponseString = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        HardwareController controllerResponse = objectMapper.readValue(controllerResponseString, HardwareController.class);
    }
}
