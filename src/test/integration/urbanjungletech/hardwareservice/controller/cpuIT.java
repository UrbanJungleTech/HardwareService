package urbanjungletech.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.exception.model.InvalidRequestError;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.hardwarecontroller.CpuHardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.CpuSensor;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        HardwareController hardwareController = new CpuHardwareController();
        hardwareController.setName("CPU Controller");
        CpuSensor sensor = new CpuSensor();
        sensor.setPort(CpuSensorType.TEMPERATURE.toString());
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
        HardwareController hardwareController = new CpuHardwareController();
        hardwareController.setType("cpu");
        hardwareController.setName("CPU Controller");
        CpuSensor sensor = new CpuSensor();
        sensor.setName("Clock Speed Sensor");
        sensor.setPort(CpuSensorType.CLOCK_SPEED.toString());
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
     * Given a controller has a sensor whose port is null
     * When a POST request is made to /hardwarecontroller/
     * Then the response status is 400
     * And the response body contains an error message
     */
    @Test
    public void testPortCannotBeNull() throws Exception {
        HardwareController hardwareController = new CpuHardwareController();
        hardwareController.setName(null);
        hardwareController.setName("CPU Controller");
        CpuSensor sensor = new CpuSensor();
        sensor.setName("Invalid Sensor");
        sensor.setPort(null);
        hardwareController.setSensors(List.of(sensor));
        String jsonResponse = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        InvalidRequestError error = objectMapper.readValue(jsonResponse, InvalidRequestError.class);
        assertEquals(1, error.getFields().size());
        assertEquals("sensors[0].port", error.getFields().get(0).getField());
        assertEquals("Port cannot be null", error.getFields().get(0).getReason());
        assertEquals("Invalid request", error.getMessage());
    }

    /**
     * Given a controller which has a sensor with an invalid sensorType
     * When a POST request is made to /hardwarecontroller/
     * Then the response status is 400
     * And the response body contains an error message
     */
    @Test
    public void testInvalidSensorType() throws Exception {
        HardwareController hardwareController = new CpuHardwareController();
        hardwareController.setName("CPU Controller");
        CpuSensor sensor = new CpuSensor();
        sensor.setName("Invalid Sensor");
        sensor.setPort("INVALID_SENSOR_TYPE");
        hardwareController.setSensors(List.of(sensor));
        String jsonResponse = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        InvalidRequestError error = objectMapper.readValue(jsonResponse, InvalidRequestError.class);
        assertEquals(1, error.getFields().size());
        assertEquals("sensors[0].port", error.getFields().get(0).getField());
        assertEquals("Invalid CPU sensor type", error.getFields().get(0).getReason());
        assertEquals("Invalid request", error.getMessage());
    }
}
