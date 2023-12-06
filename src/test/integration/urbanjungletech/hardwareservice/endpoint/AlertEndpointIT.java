package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.model.alert.action.HardwareStateChangeAlertAction;
import urbanjungletech.hardwareservice.model.alert.action.LoggingAlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.ThresholdType;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlertEndpointIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareTestService hardwareTestService;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;


    /**
     * Given an id which is not associated with a SensorReadingAlert
     * When a GET request is made to /sensorreadingalert/{id}
     * Then a 404 is returned
     */
    @Test
    void getSensorReadingAlertByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/alert/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a SensorReadingAlert
     * When a DELETE request is made to /sensorreadingalert/{id}
     * Then a 404 is returned
     */
    @Test
    void deleteSensorReadingAlertByIdNotFound() throws Exception {
        this.mockMvc.perform(delete("/alert/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a valid Alert object, with no id
     * When a POST request is made to /alert/ with the Alert object
     * Then the Alert object is returned
     */

    @Test
    void createSensorReadingAlert() throws Exception {
        Alert alert = new Alert();
        String requestBody = this.objectMapper.writeValueAsString(alert);
        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        assertNotNull(responseAlert.getId());
    }

    /**
     * Given an Alert has been created via the API
     * When a GET request is made to /alert/{id}
     * Then the Alert object is returned
     */
    @Test
    void getSensorReadingAlertById() throws Exception {
        Alert alert = new Alert();
        String requestBody = this.objectMapper.writeValueAsString(alert);
        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        String getResponse = this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert getResponseAlert = this.objectMapper.readValue(getResponse, Alert.class);
        assertEquals(responseAlert.getId(), getResponseAlert.getId());
    }

    /**
     * Given an Alert has been created via the API
     * When a DELETE request is made to /alert/{id}
     * Then the Alert object is deleted
     * And a 204 is returned
     * And a GET request to /alert/{id} returns a 404
     */
    @Test
    void deleteSensorReadingAlertById() throws Exception {
        Alert alert = new Alert();
        String requestBody = this.objectMapper.writeValueAsString(alert);
        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        this.mockMvc.perform(delete("/alert/" + responseAlert.getId()))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a valid Alert object, with a single Action of type HardwareStateChangeAlertAction
     * When a POST request is made to /alert/ with the Alert object
     * Then the Alert object is returned
     * And the Action object is returned in the list of Actions
     * And the Action object has an id
     */
    @Test
    void createSensorReadingAlertWithHardwareStateChangeAlertAction() throws Exception {
        Alert alert = new Alert();
        HardwareStateChangeAlertAction hardwareStateChangeAlertAction = new HardwareStateChangeAlertAction();
        hardwareStateChangeAlertAction.setHardwareId(1L);
        hardwareStateChangeAlertAction.setOnoff(ONOFF.ON);
        hardwareStateChangeAlertAction.setLevel(100L);
        alert.getActions().add(hardwareStateChangeAlertAction);
        String requestBody = this.objectMapper.writeValueAsString(alert);
        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        assertNotNull(responseAlert.getId());
        assertEquals(1, responseAlert.getActions().size());
        assertEquals(HardwareStateChangeAlertAction.class.getSimpleName(), responseAlert.getActions().get(0).getClass().getSimpleName());
        assertNotNull(responseAlert.getActions().get(0).getId());
        assertEquals(responseAlert.getId(), responseAlert.getActions().get(0).getAlertId());
        HardwareStateChangeAlertAction responseHardwareStateChangeAlertAction = (HardwareStateChangeAlertAction) responseAlert.getActions().get(0);
        assertEquals(1L, responseHardwareStateChangeAlertAction.getHardwareId());
        assertEquals(hardwareStateChangeAlertAction.getOnoff(), responseHardwareStateChangeAlertAction.getOnoff());
        assertEquals(hardwareStateChangeAlertAction.getLevel(), responseHardwareStateChangeAlertAction.getLevel());
    }

    /**
     * Given a valid Alert object, with a single Action of type LoggingAlertAction
     * When a POST request is made to /alert/ with the Alert object
     * Then the Alert object is returned
     * And the Action object is returned in the list of Actions
     * And the Action object has an id
     */
    @Test
    void createSensorReadingAlertWithLoggingAlertAction() throws Exception {
        Alert alert = new Alert();
        alert.getActions().add(new LoggingAlertAction());
        String requestBody = this.objectMapper.writeValueAsString(alert);
        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        assertNotNull(responseAlert.getId());
        assertEquals(1, responseAlert.getActions().size());
        assertEquals(LoggingAlertAction.class.getSimpleName(), responseAlert.getActions().get(0).getClass().getSimpleName());
        assertNotNull(responseAlert.getActions().get(0).getId());
        assertEquals(responseAlert.getId(), responseAlert.getActions().get(0).getAlertId());
    }

    /**
     * Given a valid Alert object, with a single Condition of type SensorReadingAlertCondition
     * When a POST request is made to /alert/ with the Alert object
     * Then the Alert object is returned
     * And the Condition object is returned in the list of Conditions
     * And the Condition object has an id
     * And the Condition object has an alertId which matches the Alert object
     */
    @Test
    void createSensorReadingAlertWithSensorReadingAlertCondition() throws Exception {
        Alert alert = new Alert();
        SensorReadingAlertCondition sensorReadingAlertCondition = new SensorReadingAlertCondition();
        sensorReadingAlertCondition.setSensorId(1L);
        sensorReadingAlertCondition.setThreshold(10L);
        sensorReadingAlertCondition.setThresholdType(ThresholdType.ABOVE);

        AlertConditions alertConditions = new AlertConditions();
        alertConditions.getConditions().add(sensorReadingAlertCondition);

        alert.setConditions(alertConditions);

        String requestBody = this.objectMapper.writeValueAsString(alert);
        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        assertNotNull(responseAlert.getId());
        AlertConditions responseAlertConditions = responseAlert.getConditions();
        assertNotNull(responseAlertConditions);
        assertEquals(1, responseAlertConditions.getConditions().size());
        assertEquals(1, responseAlert.getConditions().getInactiveConditions().size());
        assertEquals(0, responseAlert.getConditions().getActiveConditions().size());
        assertEquals(SensorReadingAlertCondition.class.getSimpleName(), responseAlertConditions.getConditions().get(0).getClass().getSimpleName());
        SensorReadingAlertCondition responseSensorReadingAlertCondition = (SensorReadingAlertCondition) responseAlertConditions.getConditions().get(0);
        assertNotNull(responseSensorReadingAlertCondition.getId());
        assertEquals(responseAlert.getId(), responseSensorReadingAlertCondition.getAlertId());
        assertEquals(sensorReadingAlertCondition.getSensorId(), responseSensorReadingAlertCondition.getSensorId());
        assertEquals(sensorReadingAlertCondition.getThreshold(), responseSensorReadingAlertCondition.getThreshold());
        assertEquals(sensorReadingAlertCondition.getThresholdType(), responseSensorReadingAlertCondition.getThresholdType());
    }

    /**
     * Given a valid Alert object, with a single Condition of type HardwareStateChangeAlertCondition
     * When a POST request is made to /alert/ with the Alert object
     * Then the Alert object is returned
     * And the Condition object is returned in the list of Conditions
     * And the Condition object has an id
     * And the Condition object has an alertId which matches the Alert object
     * And the Condition object has a hardwareId which matches the HardwareStateChangeAlertAction
     * And the Condition object has an onoff which matches the HardwareStateChangeAlertAction
     * And the Condition object has a level which matches the HardwareStateChangeAlertAction
    */
    @Test
    void createSensorReadingAlertWithHardwareStateChangeAlertCondition() throws Exception {
        Alert alert = new Alert();
        HardwareStateChangeAlertCondition hardwareStateChangeAlertCondition = new HardwareStateChangeAlertCondition();
        hardwareStateChangeAlertCondition.setHardwareId(1L);
        hardwareStateChangeAlertCondition.setState(ONOFF.ON);

        AlertConditions alertConditions = new AlertConditions();
        alertConditions.getConditions().add(hardwareStateChangeAlertCondition);

        alert.setConditions(alertConditions);

        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(alert)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);
        assertNotNull(responseAlert.getId());
        AlertConditions responseAlertConditions = responseAlert.getConditions();
        assertNotNull(responseAlertConditions);
        assertEquals(HardwareStateChangeAlertCondition.class.getSimpleName(), responseAlertConditions.getConditions().get(0).getClass().getSimpleName());
        HardwareStateChangeAlertCondition responseHardwareStateChangeAlertCondition = (HardwareStateChangeAlertCondition) responseAlertConditions.getConditions().get(0);
        assertNotNull(responseHardwareStateChangeAlertCondition.getId());
        assertEquals(responseAlert.getId(), responseHardwareStateChangeAlertCondition.getAlertId());
        assertEquals(hardwareStateChangeAlertCondition.getHardwareId(), responseHardwareStateChangeAlertCondition.getHardwareId());
        assertEquals(hardwareStateChangeAlertCondition.getState(), responseHardwareStateChangeAlertCondition.getState());
    }

    /**
     * Given a valid Alert object has been created, with a single Condition of type HardwareStateChangeAlertCondition and a single Action of type LoggingAlertAction
     * When the state of the hardware associated with the HardwareStateChangeAlertCondition changes
     * Then the action is executed
     */
    @Test
    void hardwareStateChangeAlertConditionActionExecution() throws Exception {
        //create a hardware controller with a hardware.
        HardwareController hardwareController = this.hardwareTestService.createBasicHardware();
        Hardware hardware = hardwareController.getHardware().get(0);
        long hardwareId = hardware.getId();

        Alert alert = new Alert();
        HardwareStateChangeAlertCondition hardwareStateChangeAlertCondition = new HardwareStateChangeAlertCondition();
        hardwareStateChangeAlertCondition.setHardwareId(hardwareId);
        hardwareStateChangeAlertCondition.setState(ONOFF.ON);

        LoggingAlertAction loggingAlertAction = new LoggingAlertAction();
        alert.getActions().add(loggingAlertAction);

        AlertConditions alertConditions = new AlertConditions();
        alertConditions.getConditions().add(hardwareStateChangeAlertCondition);

        alert.setConditions(alertConditions);

        String response = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(alert)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Alert responseAlert = this.objectMapper.readValue(response, Alert.class);

        //update the hardware state
        hardware.getDesiredState().setState(ONOFF.ON);
        this.mockMvc.perform(put("/hardware/" + hardwareId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(hardware)))
                .andExpect(status().isOk());

        Thread.sleep(10000);

    }

}
