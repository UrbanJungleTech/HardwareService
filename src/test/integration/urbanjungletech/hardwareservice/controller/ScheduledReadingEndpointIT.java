package urbanjungletech.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.ScheduledSensorReadingTestService;
import urbanjungletech.hardwareservice.SensorTestService;
import urbanjungletech.hardwareservice.model.AlertType;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;

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

    @Autowired
    ScheduledSensorReadingTestService scheduledSensorReadingTestService;

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
        ScheduledSensorReading scheduledReading = this.scheduledSensorReadingTestService.createBasicScheduledReading();
        Long scheduledReadingId = scheduledReading.getId();
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
        ScheduledSensorReading scheduledReading = this.scheduledSensorReadingTestService.createBasicScheduledReading();

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

    /**
     * Given a ScheduledReading has been created.
     * And Given a SensorReadingAlert object
     * When a POST request is sent to /scheduledreading/{scheduledReadingId}/sensorreadingalerts with the SensorReadingAlert object
     * Then the response is 201
     * And the SensorReadingAlert is returned
     */
    @Test
    void addSensorReadingAlert() throws Exception {
        ScheduledSensorReading scheduledReading = this.scheduledSensorReadingTestService.createBasicScheduledReading();
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        sensorReadingAlert.setAlertType(AlertType.MAX);
        sensorReadingAlert.setThreshold(100.0);
        this.mockmvc.perform(post("/scheduledreading/" + scheduledReading.getId() + "/sensorreadingalerts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sensorReadingAlert)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.alertType").value(sensorReadingAlert.getAlertType().toString()))
                .andExpect(jsonPath("$.threshold").value(sensorReadingAlert.getThreshold()))
                .andExpect(jsonPath("$.scheduledSensorReadingId").value(scheduledReading.getId()))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    /**
     * Given a ScheduledSensorReading has been created with a single SensorReadingAlert.
     * When a GET request is sent to /scheduledreading/{scheduledReadingId}/sensorreadingalerts
     * Then the response is 200
     * And the SensorReadingAlert is returned in the list of sensorReadingAlerts
     */
    @Test
    void getSensorReadingAlerts() throws Exception {
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        sensorReadingAlert.setAlertType(AlertType.MAX);
        sensorReadingAlert.setThreshold(100.0);
        ScheduledSensorReading scheduledReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert);

        this.mockmvc.perform(get("/scheduledreading/" + scheduledReading.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorReadingAlerts[0].alertType").value(sensorReadingAlert.getAlertType().toString()))
                .andExpect(jsonPath("$.sensorReadingAlerts[0].threshold").value(sensorReadingAlert.getThreshold()))
                .andExpect(jsonPath("$.sensorReadingAlerts[0].scheduledSensorReadingId").value(scheduledReading.getId()))
                .andExpect(jsonPath("$.sensorReadingAlerts[0].id").isNotEmpty());
    }

    /**
     * Given a ScheduledSensorReading has been created with two SensorReadingAlert entities.
     * When a GET request is sent to /scheduledreading/{scheduledReadingId}/sensorreadingalerts
     * Then the response is 200
     * And the SensorReadingAlert entities are returned in the list of sensorReadingAlerts
     */
    @Test
    void getSensorReadingAlertsMultiple() throws Exception {
        SensorReadingAlert sensorReadingAlert1 = new SensorReadingAlert();
        sensorReadingAlert1.setAlertType(AlertType.MAX);
        sensorReadingAlert1.setThreshold(100.0);
        SensorReadingAlert sensorReadingAlert2 = new SensorReadingAlert();
        sensorReadingAlert2.setAlertType(AlertType.MIN);
        sensorReadingAlert2.setThreshold(0.0);
        ScheduledSensorReading scheduledReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert1, sensorReadingAlert2);

        this.mockmvc.perform(get("/scheduledreading/" + scheduledReading.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorReadingAlerts[0].alertType").value(sensorReadingAlert1.getAlertType().toString()))
                .andExpect(jsonPath("$.sensorReadingAlerts[0].threshold").value(sensorReadingAlert1.getThreshold()))
                .andExpect(jsonPath("$.sensorReadingAlerts[0].scheduledSensorReadingId").value(scheduledReading.getId()))
                .andExpect(jsonPath("$.sensorReadingAlerts[0].id").isNotEmpty())
                .andExpect(jsonPath("$.sensorReadingAlerts[1].alertType").value(sensorReadingAlert2.getAlertType().toString()))
                .andExpect(jsonPath("$.sensorReadingAlerts[1].threshold").value(sensorReadingAlert2.getThreshold()))
                .andExpect(jsonPath("$.sensorReadingAlerts[1].scheduledSensorReadingId").value(scheduledReading.getId()))
                .andExpect(jsonPath("$.sensorReadingAlerts[1].id").isNotEmpty());
    }


}
