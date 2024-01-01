package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.exception.exception.WebRequestException;
import urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller.MockHardwareController;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "development.mqtt.client.enabled=false",
        "mqtt.server.enabled=false",
        "mqtt-rpc.enabled=false"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HardwareControllerEndpointIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    HardwareControllerTestService hardwareControllerTestService;

    /**
     * Given a valid HardwareController with no child entities
     * When a POST request is made to /hardware
     * Then a 201 status code is returned
     * And the HardwareController is returned
     * And the HardwareController is saved to the database
     */
    @Test
    void createHardwareController_whenGivenAValidHardwareController_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);


        assertEquals(hardwareController.getName(), createdHardwareController.getName());
    }

    /**
     * Given a Hardwarecontroller with a single hardware, which does not have a desired state set and has its off state set to "off"
     * When a POST request is made to /hardwarecontroller/
     * The returned HardwareController contains a Hardware with a desired state set to "off"
     * The returned HardwareController contains a Hardware with a current state set to "off"
     * The returned HardwareController contains a Hardware with a a list of states containing "on" and "off"
     */

    @Test
    void createHardwareController_whenGivenAHardwareControllerWithAHardware_shouldReturnTheHardwareControllerWithTheHardware() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardware.setName("hardware1");
        hardware.setType("temperature");
        hardware.setOffState("off");
        hardware.setPossibleStates(List.of("on", "off"));
        hardwareController.getHardware().add(hardware);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        assertEquals("off", createdHardware.getDesiredState().getState());
        assertEquals("off", createdHardware.getCurrentState().getState());
        assertEquals(List.of("on", "off"), createdHardware.getPossibleStates());
    }

    /**
     * Given a Hardwarecontroller with a single hardware, which does not have a desired state set and has its off state set to "off"
     * When a GET request is made to /hardware/{hardwareId}
     * The returned Hardware contains a desired state set to "off"
     * The returned Hardware contains a current state set to "off"
     * The returned Hardware contains a list of states containing "on" and "off"
     */
    @Test
    void getHardwareControllerHardware_whenGivenAHardwareControllerWithAHardware_shouldReturnTheHardware() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        long hardwareId = createdHardware.getId();

        //get the hardware controller
        String responseJson = mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Hardware retrievedHardware = objectMapper.readValue(responseJson, Hardware.class);
        assertEquals("off", retrievedHardware.getDesiredState().getState());
        assertEquals("off", retrievedHardware.getCurrentState().getState());
        assertEquals(List.of("on", "off"), retrievedHardware.getPossibleStates());
    }

    /**
     * Given a Hardwarecontroller with a single hardware, which has a desired state set to on and has its off state set to "off"
     * When a GET request is made to /hardware/{hardwareId}
     * The returned Hardware contains a desired state set to "on"
     * The returned Hardware contains a current state set to "off"
     * The returned Hardware contains a list of states containing "on" and "off"
     */
    @Test
    void getHardwareControllerHardware_whenGivenAHardwareControllerWithAHardwareWithADesiredState_shouldReturnTheHardware() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState("on");
        hardwareController.getHardware().get(0).setDesiredState(hardwareState);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        long hardwareId = createdHardware.getId();

        //get the hardware controller
        String responseJson = mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Hardware retrievedHardware = objectMapper.readValue(responseJson, Hardware.class);
        assertEquals("on", retrievedHardware.getDesiredState().getState());
        assertEquals("off", retrievedHardware.getCurrentState().getState());
        assertEquals(List.of("on", "off"), retrievedHardware.getPossibleStates());
    }


    /**
     * Given a HardwareController is created via /hardwarecontroller/
     * When a DELETE request is made to /hardwarecontroller/{hardwareControllerId}
     * Then a 204 status code is returned
     * And the HardwareController is deleted from the database
     */
    @Test
    void deleteHardwareController_whenGivenAValidHardwareController_shouldDeleteTheHardwareController() throws Exception {

        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        HardwareController createdHardwareController = hardwareControllerTestService.postHardwareController(hardwareController);

        //delete the hardware controller
        mockMvc.perform(delete("/hardwarecontroller/" + createdHardwareController.getId()))
                .andExpect(status().isNoContent());

        //check the hardware controller was deleted
        mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId()))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a valid HardwareController with no child entities has been created via /hardwarecontroller/
     * When a PUT request is made to /hardwarecontroller/{hardwareControllerId} with a new name and serial number
     * Then a 200 status code is returned
     * And the HardwareController is returned
     * And the HardwareController is updated in the database
     * And the HardwareController's id is unchanged
     * And the HardwareController's name and serial number are updated
     */
    @Test
    void updateHardwareController_whenGivenAValidHardwareController_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        HardwareController createdHardwareController = hardwareControllerTestService.postHardwareController(hardwareController);

        //update the hardware controller
        createdHardwareController.setName("Updated Name");

        String hardwareControllerJson = objectMapper.writeValueAsString(createdHardwareController);
        String responseJson = mockMvc.perform(put("/hardwarecontroller/" + createdHardwareController.getId())
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HardwareController updatedHardwareController = objectMapper.readValue(responseJson, HardwareController.class);

        //check the values in the response
        assertEquals(createdHardwareController.getName(), updatedHardwareController.getName());
        assertEquals(createdHardwareController.getId(), updatedHardwareController.getId());
    }

    /**
     * Given a HardwareController with a single Hardware has been created via /hardwarecontroller/
     * When a PUT request is made to /hardwarestate/{hardwareStateId} with a new state of "on"
     * Then a 200 status code is returned
     * And the HardwareState is returned
     * And a call to /hardware/{hardwareId} returns a Hardware with a current state of "on"
     */
    @Test
    void updateHardwareState_whenGivenAValidHardwareState_shouldReturnTheHardwareState() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        HardwareController createdHardwareController = hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        long hardwareId = createdHardware.getId();

        //update the hardware state
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState("on");
        long hardwareStateId = createdHardware.getDesiredState().getId();
        String hardwareStateJson = objectMapper.writeValueAsString(hardwareState);
        String responseJson = mockMvc.perform(put("/hardwarestate/" + hardwareStateId)
                        .content(hardwareStateJson)
                        .contentType("application/json")
                        .content(hardwareStateJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HardwareState updatedHardwareState = objectMapper.readValue(responseJson, HardwareState.class);

        //check the values in the response
        assertEquals(hardwareState.getState(), updatedHardwareState.getState());

        //check the hardware state was updated
        responseJson = mockMvc.perform(get("/hardware/" + hardwareId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Hardware retrievedHardware = objectMapper.readValue(responseJson, Hardware.class);
        assertEquals("on", retrievedHardware.getDesiredState().getState());
        assertEquals("off", retrievedHardware.getCurrentState().getState());
    }

    /**
     * Given a valid HardwareController with no child entities has been created via /hardwarecontroller/
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}
     * Then a 200 status code is returned
     * And the HardwareController is returned
     */
    @Test
    void getHardwareController_whenGivenAValidHardwareControllerId_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        HardwareController createdHardwareController = hardwareControllerTestService.postHardwareController(hardwareController);

        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        //get the hardware controller
        String hardwareResponseString = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId())
                        .content(hardwareControllerJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HardwareController retrievedHardwareController = objectMapper.readValue(hardwareResponseString, HardwareController.class);

        //check the values in the response
        assertEquals(hardwareController.getName(), retrievedHardwareController.getName());
        assertEquals(createdHardwareController.getId(), retrievedHardwareController.getId());
        assertEquals(hardwareController.getType(), retrievedHardwareController.getType());

    }

    /**
     * Given an id which is not associated with an existing HardwareController
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}
     * Then a 404 status code is returned
     * And the response body contains json with the following fields:
     * - status: 404
     * - message: "Hardware Controller not found with id of " + id
     */
    @Test
    void getHardwareController_whenGivenAnInvalidHardwareControllerId_shouldReturn404() throws Exception {
        MvcResult result = mockMvc.perform(get("/hardwarecontroller/1"))
                .andExpect(status().isNotFound())
                .andReturn();
        WebRequestException errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), WebRequestException.class);
        assertEquals(404, errorResponse.getHttpStatus());
        assertEquals("Hardware Controller not found with id of 1", errorResponse.getMessage());
    }

    /**
     * Given a HardwareController was made via /hardwarecontroller/
     * When a GET request is made to /hardwarecontroller/
     * Then a 200 status code is returned
     * The response is a list containing the HardwareController
     */
    @Test
    void getAllHardwareControllers_whenHardwareControllersExist_shouldReturnAListOfHardwareControllers() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        this.hardwareControllerTestService.postHardwareController(hardwareController);

        //get the hardware controller
        String jsonResponse = mockMvc.perform(get("/hardwarecontroller/")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<HardwareController> retrievedHardwareControllers = objectMapper.readValue(jsonResponse, new TypeReference<List<HardwareController>>() {
        });

        //check the values in the response
        assertEquals(1, retrievedHardwareControllers.size());
        HardwareController createdHardwareController = retrievedHardwareControllers.get(0);
        assertEquals(hardwareController.getName(), retrievedHardwareControllers.get(0).getName());
        assertEquals(createdHardwareController.getId(), retrievedHardwareControllers.get(0).getId());
    }

    /**
     * Given a valid HardwareController with a single Hardware of type 'light'
     * When a POST request is made to /hardwarecontroller/
     * Then a 201 status code is returned
     * And the HardwareController is returned
     * And the HardwareController is saved to the database
     * And the HardwareController contains a list of Hardware of size 1
     * And the HardwareController contains a Hardware of type 'light'
     */
    @Test
    void createHardwareController_whenGivenAValidHardwareControllerWithAHardware_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = this.hardwareControllerTestService.createDefaultHardware("light");
        hardware.setOffState("off");
        hardwareController.getHardware().add(hardware);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        String responseJson = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareController createdHardwareController = objectMapper.readValue(responseJson, HardwareController.class);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        assertEquals(createdHardware.getType(), hardware.getType());
        assertNotNull(createdHardware.getId());
        assertNotNull(createdHardware.getDesiredState());
        assertNotNull(createdHardware.getDesiredState().getId());
        assertNotNull(createdHardware.getDesiredState().getState());
        assertNotNull(createdHardware.getCurrentState());
        assertNotNull(createdHardware.getCurrentState().getId());
        assertNotNull(createdHardware.getCurrentState().getState());
    }

    /**
     * Given a valid HardwareController with a single Sensor of type "temperature"
     * When a POST request is made to /hardwarecontroller/
     * Then a 201 status code is returned
     * And the HardwareController is returned
     * And the HardwareController contains a list of Sensors of size 1
     * And the HardwareController contains a Sensor of type "temperature"
     * And the sensor was saved to the database
     */
    @Test
    void createHardwareController_whenGivenAValidHardwareControllerWithASensor_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Sensor sensor = this.hardwareControllerTestService.createSensor("temperature");
        hardwareController.getSensors().add(sensor);
        HardwareController responseHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        assertEquals(1, responseHardwareController.getSensors().size());
        Sensor responseSensor = responseHardwareController.getSensors().get(0);
        assertEquals(sensor.getSensorType(), responseSensor.getSensorType());
        assertNotNull(responseSensor.getId());
        assertEquals(sensor.getConfiguration(), responseSensor.getConfiguration());
        assertEquals(sensor.getMetadata(), responseSensor.getMetadata());
        assertEquals(sensor.getName(), responseSensor.getName());
        assertEquals(sensor.getPort(), responseSensor.getPort());
    }

    /**
     * Given a HardwareController is created via /hardwarecontroller/ with 2 hardware of types light and heater
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}
     * Then a 200 status code is returned
     * And the HardwareController is returned
     * And the HardwareController contains a list of Hardware of size 2
     * And the HardwareController contains a Hardware of type 'light'
     * And the HardwareController contains a Hardware of type 'heater'
     */
    @Test
    void getHardwareController_whenGivenAHardwareControllerWith2Hardware_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        Hardware hardware = new Hardware();
        hardware.setType("light");
        hardwareController.getHardware().add(hardware);
        hardware = new Hardware();
        hardware.setType("heater");
        hardwareController.getHardware().add(hardware);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //get the hardware controller
        result = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId()))
                .andExpect(status().isOk())
                .andReturn();
        HardwareController retrievedHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //check the values in the response, cant guarantee order of list so check size and then check each item
        assertEquals(2, retrievedHardwareController.getHardware().size());
        Hardware hardware1 = retrievedHardwareController.getHardware().get(0);
        Hardware hardware2 = retrievedHardwareController.getHardware().get(1);
        if (hardware1.getType().equals("light")) {
            assertEquals("heater", hardware2.getType());
        } else {
            assertEquals("light", hardware2.getType());
        }
    }

    /**
     * Given a HardwareController is created via /hardwarecontroller/ with 2 Hardware of types light and heater
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}/hardware
     * Then a 200 status code is returned
     * And the list of Hardware is returned
     */
    @Test
    void getHardwareControllerHardware_whenGivenAHardwareControllerWith2Hardware_shouldReturnTheHardware() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        Hardware hardware = new Hardware();
        hardware.setType("light");
        hardwareController.getHardware().add(hardware);
        hardware = new Hardware();
        hardware.setType("heater");
        hardwareController.getHardware().add(hardware);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //get the hardware controller
        result = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId() + "/hardware"))
                .andExpect(status().isOk())
                .andReturn();
        List<Hardware> retrievedHardware = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Hardware>>() {
        });

        //check the values in the response, cant guarantee order of list so check size and then check each item
        assertEquals(2, retrievedHardware.size());
        Hardware hardware1 = retrievedHardware.get(0);
        Hardware hardware2 = retrievedHardware.get(1);
        if (hardware1.getType().equals("light")) {
            assertEquals("heater", hardware2.getType());
        } else {
            assertEquals("light", hardware2.getType());
        }
    }

    /**
     * Given a HardwareController is created via /hardwarecontroller/
     * Given a valid Hardware of type 'light'
     * When a POST request is made to /hardwarecontroller/{hardwareControllerId}/hardware with the Hardware
     * Then a 201 status code is returned
     * And the Hardware is returned
     * And the HardwareController contains a list of Hardware of size 1
     */
    @Test
    void createHardwareControllerHardware_whenGivenAValidHardware_shouldReturnTheHardware() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //create the hardware
        Hardware hardware = new Hardware();
        hardware.setType("light");
        String hardwareJson = objectMapper.writeValueAsString(hardware);
        result = mockMvc.perform(post("/hardwarecontroller/" + createdHardwareController.getId() + "/hardware")
                        .content(hardwareJson)
                        .contentType("application/json")
                        .content(hardwareJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    /**
     * Given an id which is not associated with a HardwareController
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}/hardware
     * Then a 404 status code is returned
     * The response contains an error message
     * - "HttpStatus: 404"
     * - "HardwareController with id {hardwareControllerId} not found"
     */
    @Test
    void getHardwareControllerHardware_whenGivenAnInvalidHardwareControllerId_shouldReturn404() throws Exception {
        //get the hardware controller
        mockMvc.perform(get("/hardwarecontroller/1/hardware")
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Hardware Controller not found with id of 1"))
                .andExpect(jsonPath("$.httpStatus").value("404"));
    }

    /**
     * Given a HardwareController is created via /hardwarecontroller/
     * Given a valid Sensor of type 'temperature'
     * When a POST request is made to /hardwarecontroller/{hardwareControllerId}/sensor with the Sensor
     * Then a 201 status code is returned
     * And the Sensor is returned
     * And the HardwareController contains a list of Sensors of size 1
     */
    @Test
    void createHardwareControllerSensor_whenGivenAValidSensor_shouldReturnTheSensor() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //create the sensor
        Sensor sensor = new Sensor();
        sensor.setSensorType("temperature");
        sensor.setPort("1");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("name", "temperature");
        sensor.setMetadata(metadata);
        sensor.setName("Test Sensor");

        Map<String, String> sensorConfiguration = new HashMap<>();
        sensorConfiguration.put("readingTarget", "temperature");
        sensor.setConfiguration(sensorConfiguration);

        String sensorJson = objectMapper.writeValueAsString(sensor);
        result = mockMvc.perform(post("/hardwarecontroller/" + createdHardwareController.getId() + "/sensor")
                        .content(sensorJson)
                        .contentType("application/json")
                        .content(sensorJson))
                .andExpect(status().isCreated())
                .andReturn();
        Sensor responseSensor = objectMapper.readValue(result.getResponse().getContentAsString(), Sensor.class);
        assertEquals(sensor.getSensorType(), responseSensor.getSensorType());
        assertNotNull(responseSensor.getId());
        assertEquals(sensor.getConfiguration(), responseSensor.getConfiguration());
        assertEquals(sensor.getMetadata(), responseSensor.getMetadata());
        assertEquals(sensor.getName(), responseSensor.getName());
        assertEquals(sensor.getPort(), responseSensor.getPort());
    }

    /**
     * Given a HardwareController is created via /hardwarecontroller/ with 2 Sensors
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}/sensor
     * Then a 200 status code is returned
     * And the Sensors are returned
     * And the HardwareController contains a list of Sensors of size 2 containing the Sensors
     */
    @Test
    void getHardwareControllerSensors_whenGivenAValidHardwareControllerId_shouldReturnTheSensors() throws Exception {
        HardwareController hardwareController = new MockHardwareController();
        hardwareController.setName("Test Hardware Controller");
        Sensor sensor = new Sensor();
        sensor.setSensorType("temperature");
        hardwareController.getSensors().add(sensor);
        sensor = new Sensor();
        sensor.setSensorType("humidity");
        hardwareController.getSensors().add(sensor);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //get the hardware controller
        result = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId() + "/sensor"))
                .andExpect(status().isOk())
                .andReturn();
        List<Sensor> retrievedSensors = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Sensor>>() {
        });

        //check the values in the response, cant guarantee order of list so check size and then check each item
        assertEquals(2, retrievedSensors.size());
        Sensor sensor1 = retrievedSensors.get(0);
        Sensor sensor2 = retrievedSensors.get(1);
        if (sensor1.getSensorType().equals("temperature")) {
            assertEquals("humidity", sensor2.getSensorType());
        } else {
            assertEquals("temperature", sensor2.getSensorType());
        }
    }

    /**
     * Given an id which is not associated with a HardwareController
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}/sensor
     * Then a 404 status code is returned
     * The response contains an error message with the fields:
     * - "HttpStatus: 404"
     * - "HardwareController with id {hardwareControllerId} not found"
     */
    @Test
    void getHardwareControllerSensors_whenGivenAnInvalidHardwareControllerId_shouldReturn404() throws Exception {
        //get the hardware controller
        mockMvc.perform(get("/hardwarecontroller/1/sensor"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Hardware Controller not found with id of 1"))
                .andExpect(jsonPath("$.httpStatus").value("404"));
    }

}
