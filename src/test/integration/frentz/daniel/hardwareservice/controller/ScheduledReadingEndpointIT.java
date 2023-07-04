package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.SensorTestService;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScheduledReadingEndpointIT {

    @Autowired
    private MockMvc mockmvc;
    @Autowired
    private SensorTestService sensorTestService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;

    @BeforeEach
    void setUp() {
        this.hardwareControllerRepository.deleteAll();
    }
    /**
     * Given a Sensor has been created as part of a HardwareController
     * And a ScheduledReading has been created with the Sensor's id
     * When the ScheduledReading is deleted via /scheduledreading/{scheduledReadingId}
     * Then the response is 204
     * And the ScheduledReading is not retrievable via /scheduledreading/{scheduledReadingId}
     */
    @Test
    void deleteScheduledReadingById() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        ScheduledSensorReading scheduledSensorReading = new ScheduledSensorReading();
        scheduledSensorReading.setSensorId(sensor.getId());
        scheduledSensorReading.setCronString("0 0 0 1 1 ? 2099");
        sensor.getScheduledSensorReadings().add(scheduledSensorReading);
        String sensorString = this.mockmvc.perform(put("/sensor/" + sensor.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(sensor)))
                .andReturn().getResponse().getContentAsString();
        sensor = this.objectMapper.readValue(sensorString, Sensor.class);
        long scheduledReadingId = sensor.getScheduledSensorReadings().get(0).getId();
        this.mockmvc.perform(delete("/scheduledreading/" + scheduledReadingId))
                .andExpect(status().isNoContent());
        this.mockmvc.perform(get("/scheduledreading/" + scheduledReadingId))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a Sensor has been created as part of a HardwareController
     * And a ScheduledReading has been created with the Sensor's id
     * When the ScheduledReading is retrieved via /scheduledreading/{scheduledReadingId}
     * Then the response is 200
     * And the ScheduledReading is returned
     * And the ScheduledReading's id matches the id in the path
     */
    @Test
    void getScheduledReadingById() throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        ScheduledSensorReading scheduledSensorReading = new ScheduledSensorReading();
        scheduledSensorReading.setSensorId(sensor.getId());
        scheduledSensorReading.setCronString("0 0 0 1 1 ? 2099");
        sensor.getScheduledSensorReadings().add(scheduledSensorReading);
        String sensorString = this.mockmvc.perform(put("/sensor/" + sensor.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(sensor)))
                .andReturn().getResponse().getContentAsString();
        sensor = this.objectMapper.readValue(sensorString, Sensor.class);
        ScheduledSensorReading scheduledReading = sensor.getScheduledSensorReadings().get(0);

        this.mockmvc.perform(get("/scheduledreading/" + scheduledReading.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(scheduledReading.getId()))
                .andExpect(jsonPath("$.sensorId").value(scheduledReading.getSensorId()))
                .andExpect(jsonPath("$.cronString").value(scheduledReading.getCronString()));
    }

    /**
     * Given an id which is not associated with a ScheduledReading
     * When the ScheduledReading is retrieved via /scheduledreading/{scheduledReadingId}
     * Then the response is 404
     */
    @Test
    void getScheduledReadingByIdNotFound() throws Exception {
        this.mockmvc.perform(get("/scheduledreading/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a ScheduledReading
     * When the ScheduledReading is deleted via /scheduledreading/{scheduledReadingId}
     * Then the response is 404
     */
    @Test
    void deleteScheduledReadingByIdNotFound() throws Exception {
        this.mockmvc.perform(delete("/scheduledreading/1"))
                .andExpect(status().isNotFound());
    }
}
