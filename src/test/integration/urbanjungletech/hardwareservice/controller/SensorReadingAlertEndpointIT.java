package urbanjungletech.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.ScheduledSensorReadingTestService;
import urbanjungletech.hardwareservice.action.model.HardwareStateChangeAction;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.dao.SensorReadingAlertDAO;
import urbanjungletech.hardwareservice.model.AlertType;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SensorReadingAlertEndpointIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SensorReadingAlertDAO sensorReadingAlertDAO;
    @Autowired
    private ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    @Autowired
    private ScheduledSensorReadingTestService scheduledSensorReadingTestService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws SchedulerException {

    }

    /**
     * Given a ScheduledSensorReading has been created with a SensorReadingAlert
     * When the ScheduledSensorReading is deleted via /scheduledreading/{scheduledReadingId}
     * Then the SensorReadingAlert is deleted
     */
    @Test
    void deletingScheduledSensorReadingAlsoDeletesAssociatedAlerts() throws Exception {
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        ScheduledSensorReading scheduledReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert);
        Long scheduledReadingId = scheduledReading.getId();
        Long sensorReadingAlertId = scheduledReading.getSensorReadingAlerts().get(0).getId();
        this.mockMvc.perform(delete("/scheduledreading/" + scheduledReadingId))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/sensorreadingalert/" + sensorReadingAlertId))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a SensorReadingAlert
     * When a GET request is made to /sensorreadingalert/{id}
     * Then a 404 is returned
     */
    @Test
    void getSensorReadingAlertByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/sensorreadingalert/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a SensorReadingAlert
     * When a DELETE request is made to /sensorreadingalert/{id}
     * Then a 404 is returned
     */
    @Test
    void deleteSensorReadingAlertByIdNotFound() throws Exception {
        this.mockMvc.perform(delete("/sensorreadingalert/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a SensorReadingAlert has been created
     * When a GET request is made to /sensorreadingalert/{id}
     * Then the SensorReadingAlert is returned
     */
    @Test
    void getSensorReadingAlertById() throws Exception {
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        sensorReadingAlert.setAlertType(AlertType.MAX);
        sensorReadingAlert.setThreshold(100.0);
        ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert);
        Long sensorReadingAlertId = scheduledSensorReading.getSensorReadingAlerts().get(0).getId();
        sensorReadingAlert = scheduledSensorReading.getSensorReadingAlerts().get(0);
        String response = this.mockMvc.perform(get("/sensorreadingalert/" + sensorReadingAlertId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReadingAlert responseSensorReadingAlert = this.objectMapper.readValue(response, SensorReadingAlert.class);
        assertEquals(responseSensorReadingAlert.getId(), sensorReadingAlert.getId());
        assertEquals(responseSensorReadingAlert.getAlertType(), sensorReadingAlert.getAlertType());
        assertEquals(responseSensorReadingAlert.getThreshold(), sensorReadingAlert.getThreshold());
        assertEquals(responseSensorReadingAlert.getScheduledSensorReadingId(), sensorReadingAlert.getScheduledSensorReadingId());
    }

    /**
     * Given 2 SensorReadingAlerts have been created
     * When a GET request is made to /sensorreadingalert/
     * Then both SensorReadingAlerts are returned
     */
    @Test
    void getAllSensorReadingAlerts() throws Exception {
        SensorReadingAlert sensorReadingAlert1 = new SensorReadingAlert();
        sensorReadingAlert1.setAlertType(AlertType.MAX);
        sensorReadingAlert1.setThreshold(100.0);


        SensorReadingAlert sensorReadingAlert2 = new SensorReadingAlert();
        sensorReadingAlert2.setAlertType(AlertType.MIN);
        sensorReadingAlert2.setThreshold(0.0);


        ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert1, sensorReadingAlert2);
        sensorReadingAlert1 = scheduledSensorReading.getSensorReadingAlerts().get(0);
        Long sensorReadingAlertId1 = sensorReadingAlert1.getId();

        sensorReadingAlert2 = scheduledSensorReading.getSensorReadingAlerts().get(1);
        Long sensorReadingAlertId2 = sensorReadingAlert2.getId();


        String response = this.mockMvc.perform(get("/sensorreadingalert/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReadingAlert[] responseSensorReadingAlerts = this.objectMapper.readValue(response, SensorReadingAlert[].class);
        assertEquals(responseSensorReadingAlerts.length, 2);
        assertEquals(responseSensorReadingAlerts[0].getId(), sensorReadingAlertId1);
        assertEquals(responseSensorReadingAlerts[0].getAlertType(), sensorReadingAlert1.getAlertType());
        assertEquals(responseSensorReadingAlerts[0].getThreshold(), sensorReadingAlert1.getThreshold());
        assertEquals(responseSensorReadingAlerts[0].getScheduledSensorReadingId(), sensorReadingAlert1.getScheduledSensorReadingId());
        assertEquals(responseSensorReadingAlerts[1].getId(), sensorReadingAlertId2);
        assertEquals(responseSensorReadingAlerts[1].getAlertType(), sensorReadingAlert2.getAlertType());
        assertEquals(responseSensorReadingAlerts[1].getThreshold(), sensorReadingAlert2.getThreshold());
        assertEquals(responseSensorReadingAlerts[1].getScheduledSensorReadingId(), sensorReadingAlert2.getScheduledSensorReadingId());
    }

    /**
     * Given a SensorReadingAlert has been created
     * When a PUT request is made to /sensorreadingalert/{id} with an updated SensorReadingAlert
     * Then the SensorReadingAlert is updated
     */
    @Test
    void updateSensorReadingAlertById() throws Exception {
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        sensorReadingAlert.setAlertType(AlertType.MAX);
        sensorReadingAlert.setThreshold(100.0);
        ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert);
        Long sensorReadingAlertId = scheduledSensorReading.getSensorReadingAlerts().get(0).getId();
        sensorReadingAlert = scheduledSensorReading.getSensorReadingAlerts().get(0);
        sensorReadingAlert.setAlertType(AlertType.MIN);
        sensorReadingAlert.setThreshold(0.0);
        String requestBody = this.objectMapper.writeValueAsString(sensorReadingAlert);
        String response = this.mockMvc.perform(put("/sensorreadingalert/" + sensorReadingAlertId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReadingAlert responseSensorReadingAlert = this.objectMapper.readValue(response, SensorReadingAlert.class);
        assertEquals(responseSensorReadingAlert.getId(), sensorReadingAlert.getId());
        assertEquals(responseSensorReadingAlert.getAlertType(), sensorReadingAlert.getAlertType());
        assertEquals(responseSensorReadingAlert.getThreshold(), sensorReadingAlert.getThreshold());
        assertEquals(responseSensorReadingAlert.getScheduledSensorReadingId(), sensorReadingAlert.getScheduledSensorReadingId());
    }

    /**
     * Given an id which is not associated with a SensorReadingAlert
     * When a PUT request is made to /sensorreadingalert/{id} with an updated SensorReadingAlert
     * Then a 404 error is returned
     */
    @Test
    void updateSensorReadingAlertByIdNotFound() throws Exception {
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        sensorReadingAlert.setAlertType(AlertType.MAX);
        sensorReadingAlert.setThreshold(100.0);
        sensorReadingAlert.setId(1L);
        String requestBody = this.objectMapper.writeValueAsString(sensorReadingAlert);
        this.mockMvc.perform(put("/sensorreadingalert/" + sensorReadingAlert.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a SensorReadingAlert has been created
     * Given a HardwareStateChangeAction object
     * When a POST request is made to /sensorreadingalert/{id}/actions with the HardwareStateChangeAction object
     * Then the HardwareStateChangeAction object is returned
     * And the HardwareStateChangeAction object is associated with the SensorReadingAlert in its list of alerts
     */
    @Test
    void addHardwareStateChangeActionToSensorReadingAlert() throws Exception {
        SensorReadingAlert sensorReadingAlert = new SensorReadingAlert();
        sensorReadingAlert.setAlertType(AlertType.MAX);
        sensorReadingAlert.setThreshold(100.0);
        ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingTestService.createBasicScheduledReading(sensorReadingAlert);
        Long sensorReadingAlertId = scheduledSensorReading.getSensorReadingAlerts().get(0).getId();

        HardwareStateChangeAction hardwareStateChangeAction = new HardwareStateChangeAction();
        hardwareStateChangeAction.setHardwareId(1L);
        String requestBody = this.objectMapper.writeValueAsString(hardwareStateChangeAction);
        String response = this.mockMvc.perform(post("/sensorreadingalert/" + sensorReadingAlertId + "/actions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HardwareStateChangeAction responseHardwareStateChangeAction = this.objectMapper.readValue(response, HardwareStateChangeAction.class);

        assertNotNull(responseHardwareStateChangeAction.getId());
        assertEquals(sensorReadingAlertId, responseHardwareStateChangeAction.getSensorReadingAlertId());
        assertEquals(hardwareStateChangeAction.getHardwareId(), responseHardwareStateChangeAction.getHardwareId());

        //get the sensor reading alert again to check that the action was added to the list of actions
        response = this.mockMvc.perform(get("/sensorreadingalert/" + sensorReadingAlertId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReadingAlert responseSensorReadingAlert = this.objectMapper.readValue(response, SensorReadingAlert.class);
        assertEquals(responseSensorReadingAlert.getActions().size(), 1);
        HardwareStateChangeAction responseHardwareStateChangeAction2 = (HardwareStateChangeAction) responseSensorReadingAlert.getActions().get(0);
        assertEquals(responseHardwareStateChangeAction.getId(), responseHardwareStateChangeAction2.getId());
    }
}
