package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.mock.action.MockAction;
import urbanjungletech.hardwareservice.mock.action.MockActionService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.model.alert.action.HardwareStateChangeAlertAction;
import urbanjungletech.hardwareservice.model.alert.action.LoggingAlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.ThresholdType;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;
import urbanjungletech.hardwareservice.services.http.SensorTestService;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "mqtt.client.enabled=false", "mqtt.server.enabled=false"})
@AutoConfigureMockMvc()
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlertEndpointIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    private MockActionService mockActionService;
    @Autowired
    private HardwareTestService hardwareTestService;
    @Autowired
    SensorTestService sensorTestService;


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
        this.mockMvc.perform(delete("/alert/345"))
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
     * Given an invalid alert id
     * When a GET request is made to /alert/{id}
     * Then a 404 is returned
     */
    @Test
    void getSensorReadingAlertByIdInvalidId() throws Exception {
        this.mockMvc.perform(get("/alert/123"))
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
        hardwareStateChangeAlertAction.setState("on");
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
        assertEquals(hardwareStateChangeAlertAction.getState(), responseHardwareStateChangeAlertAction.getState());
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
        sensorReadingAlertCondition.setThreshold(1.0);
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
     * And the Condition object has a state which matches the HardwareStateChangeAlertAction
     * And the Condition object has a level which matches the HardwareStateChangeAlertAction
    */
    @Test
    void createSensorReadingAlertWithHardwareStateChangeAlertCondition() throws Exception {
        Alert alert = new Alert();
        HardwareStateChangeAlertCondition hardwareStateChangeAlertCondition = new HardwareStateChangeAlertCondition();
        hardwareStateChangeAlertCondition.setHardwareId(1L);

        hardwareStateChangeAlertCondition.setState("on");

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
     * Given a valid Alert object has been created, with a single Condition of type HardwareStateChangeAlertCondition and a single Action of type MockAction
     * When the state of the hardware associated with the HardwareStateChangeAlertCondition changes
     * Then the action is executed
     */
    @Test
    void hardwareStateChangeAlertConditionActionExecution() throws Exception {
        //create a hardware controller with a hardware.
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        long hardwareId = createdHardware.getId();

        Alert alert = new Alert();
        HardwareStateChangeAlertCondition hardwareStateChangeAlertCondition = new HardwareStateChangeAlertCondition();
        hardwareStateChangeAlertCondition.setHardwareId(hardwareId);
        hardwareStateChangeAlertCondition.setState("on");

        MockAction mockAction = new MockAction();
        alert.getActions().add(mockAction);

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
        createdHardware.getDesiredState().setState("on");
        this.mockMvc.perform(put("/hardware/" + hardwareId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(createdHardware)))
                .andExpect(status().isOk());

        //check that the actions was performed and that the alerts were updated in the alert conditions
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String getResponse = this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            Alert getResponseAlert = this.objectMapper.readValue(getResponse, Alert.class);
            assertEquals(0, getResponseAlert.getConditions().getInactiveConditions().size());
            assertEquals(1, getResponseAlert.getConditions().getActiveConditions().size());
            assertEquals(1L, mockActionService.getCounter());
        });

        /** test that updating the state in the other direction will also update the persisted states while not triggering
        the action**/
        createdHardware.getDesiredState().setState("off");
        this.mockMvc.perform(put("/hardware/" + hardwareId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(createdHardware)))
                .andExpect(status().isOk());

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String getResponse = this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            Alert getResponseAlert = this.objectMapper.readValue(getResponse, Alert.class);
            assertEquals(1, getResponseAlert.getConditions().getInactiveConditions().size());
            assertEquals(0, getResponseAlert.getConditions().getActiveConditions().size());
            assertEquals(1L, mockActionService.getCounter());
        });
    }

    /**
     * Given a valid Alert object has been created, with a single Condition of type SensorReadingAlertCondition and a single Action of type MockAction
     * When the sensor reading associated with the SensorReadingAlertCondition is below the threshold
     * Then the action is executed
     */
    @Test
    void sensorReadingAlertConditionActionExecution() throws Exception {
        //create a hardware controller with a hardware.
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/2 * * * * ?");
        scheduledReading.setSensorId(createdHardwareController.getSensors().get(0).getId());
        this.mockMvc.perform(post("/sensor/" + createdHardwareController.getSensors().get(0).getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated());

        Alert alert = new Alert();
        SensorReadingAlertCondition sensorReadingAlertCondition = new SensorReadingAlertCondition();
        sensorReadingAlertCondition.setSensorId(createdHardwareController.getSensors().get(0).getId());
        sensorReadingAlertCondition.setThreshold(0.5);
        sensorReadingAlertCondition.setThresholdType(ThresholdType.ABOVE);

        MockAction mockAction = new MockAction();
        alert.getActions().add(mockAction);

        AlertConditions alertConditions = new AlertConditions();
        alertConditions.getConditions().add(sensorReadingAlertCondition);

        alert.setConditions(alertConditions);


        String responseAlertJson = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(alert)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Alert responseAlert = this.objectMapper.readValue(responseAlertJson, Alert.class);

        //check that the actions was performed and that the alerts were updated in the alert conditions
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String getResponse = this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            Alert getResponseAlert = this.objectMapper.readValue(getResponse, Alert.class);
            assertEquals(0, getResponseAlert.getConditions().getInactiveConditions().size());
            assertEquals(1, getResponseAlert.getConditions().getActiveConditions().size());
            assertEquals(1L, mockActionService.getCounter());
        });
    }

    /**
     * Given a valid Alert object has been created, with a single Condition of type SensorReadingAlertCondition and a single Action of type MockAction
     * When the sensor reading associated with the SensorReadingAlertCondition is above the threshold
     * Then the action is not executed
     */
    @Test
    void sensorReadingAlertConditionActionExecutionAboveThreshold() throws Exception {
        //create a hardware controller with a hardware.
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/2 * * * * ?");
        scheduledReading.setSensorId(createdHardwareController.getSensors().get(0).getId());
        this.mockMvc.perform(post("/sensor/" + createdHardwareController.getSensors().get(0).getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated());

        Alert alert = new Alert();
        SensorReadingAlertCondition sensorReadingAlertCondition = new SensorReadingAlertCondition();
        sensorReadingAlertCondition.setSensorId(createdHardwareController.getSensors().get(0).getId());
        sensorReadingAlertCondition.setThreshold(1.5);
        sensorReadingAlertCondition.setThresholdType(ThresholdType.BELOW);
        sensorReadingAlertCondition.setActive(true);

        MockAction mockAction = new MockAction();
        alert.getActions().add(mockAction);

        AlertConditions alertConditions = new AlertConditions();
        alertConditions.getConditions().add(sensorReadingAlertCondition);

        alert.setConditions(alertConditions);

        String responseAlertJson = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(alert)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Alert responseAlert = this.objectMapper.readValue(responseAlertJson, Alert.class);

        //check that the actions was performed and that the alerts were updated in the alert conditions
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String getResponse = this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            Alert getResponseAlert = this.objectMapper.readValue(getResponse, Alert.class);
            assertEquals(1, getResponseAlert.getConditions().getInactiveConditions().size());
            assertEquals(0, getResponseAlert.getConditions().getActiveConditions().size());
            assertEquals(0L, mockActionService.getCounter());
        });
    }

    /**
     * Given a valid Alert object has been created, with a single Condition of type SensorReadingAlertCondition and a single Action of type MockAction
     * When the sensor reading associated with the SensorReadingAlertCondition is above the threshold
     * Then the action is executed
     */
    @Test
    void sensorReadingAlertConditionActionExecutionBelowThreshold() throws Exception {
        //create a hardware controller with a hardware.
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");
        scheduledReading.setSensorId(createdHardwareController.getSensors().get(0).getId());
        this.mockMvc.perform(post("/sensor/" + createdHardwareController.getSensors().get(0).getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated());

        Alert alert = new Alert();
        SensorReadingAlertCondition sensorReadingAlertCondition = new SensorReadingAlertCondition();
        sensorReadingAlertCondition.setSensorId(createdHardwareController.getSensors().get(0).getId());
        sensorReadingAlertCondition.setThreshold(1.5);
        sensorReadingAlertCondition.setThresholdType(ThresholdType.BELOW);

        MockAction mockAction = new MockAction();
        alert.getActions().add(mockAction);

        AlertConditions alertConditions = new AlertConditions();
        alertConditions.getConditions().add(sensorReadingAlertCondition);

        alert.setConditions(alertConditions);

        String responseAlertJson = this.mockMvc.perform(post("/alert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(alert)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Alert responseAlert = this.objectMapper.readValue(responseAlertJson, Alert.class);

        //check that the actions was performed and that the alerts were updated in the alert conditions
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String getResponse = this.mockMvc.perform(get("/alert/" + responseAlert.getId()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            Alert getResponseAlert = this.objectMapper.readValue(getResponse, Alert.class);
            assertEquals(0, getResponseAlert.getConditions().getInactiveConditions().size());
            assertEquals(1, getResponseAlert.getConditions().getActiveConditions().size());
            assertEquals(1L, mockActionService.getCounter());
        });
    }
}
