package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.exception.StandardError;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class HardwareControllerEndpointIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;
    @Autowired
    private HardwareRepository hardwareRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        hardwareControllerRepository.deleteAll();
        hardwareControllerRepository.flush();
        hardwareRepository.deleteAll();
        hardwareRepository.flush();
    }

    /**
     * Given a valid HardwareController with no child entities
     * When a POST request is made to /hardware
     * Then a 201 status code is returned
     * And the HardwareController is returned
     * And the HardwareController is saved to the database
     */
    @Test
    void createHardwareController_whenGivenAValidHardwareController_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //check the entity was saved to the db and its id is non zero
        HardwareControllerEntity hardwareControllerEntity = hardwareControllerRepository.findAll().get(0);
        assertNotEquals(0, hardwareControllerEntity.getId());

        //check the values in the response
        assertEquals(hardwareController.getSerialNumber(), createdHardwareController.getSerialNumber());
        assertEquals(hardwareController.getName(), createdHardwareController.getName());
        assertEquals(hardwareControllerEntity.getId(), createdHardwareController.getId());
    }

    /**
     * Given a HardwareController is created via /hardwarecontroller/
     * When a DELETE request is made to /hardwarecontroller/{hardwareControllerId}
     * Then a 204 status code is returned
     * And the HardwareController is deleted from the database
     */
    @Test
    void deleteHardwareController_whenGivenAValidHardwareController_shouldDeleteTheHardwareController() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //check the entity was saved to the db and its id is non zero
        HardwareControllerEntity hardwareControllerEntity = hardwareControllerRepository.findAll().get(0);

        //delete the hardware controller
        mockMvc.perform(delete("/hardwarecontroller/" + createdHardwareController.getId()))
                .andExpect(status().isNoContent());

        //check the entity was deleted from the db
        assertEquals(0, hardwareControllerRepository.findAll().size());
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
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //update the hardware controller
        hardwareController.setName("Updated Name");
        hardwareController.setSerialNumber("987654321");
        hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        result = mockMvc.perform(put("/hardwarecontroller/" + createdHardwareController.getId())
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isOk())
                .andReturn();
        HardwareController updatedHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //check the entity was saved to the db and its id is non zero
        HardwareControllerEntity hardwareControllerEntity = hardwareControllerRepository.findAll().get(0);
        assertNotEquals(0, hardwareControllerEntity.getId());

        //check the values in the response
        assertEquals(hardwareController.getSerialNumber(), updatedHardwareController.getSerialNumber());
        assertEquals(hardwareController.getName(), updatedHardwareController.getName());
        assertEquals(hardwareControllerEntity.getId(), updatedHardwareController.getId());
    }

    /**
     * Given a valid HardwareController with no child entities has been created via /hardwarecontroller/
     * When a GET request is made to /hardwarecontroller/{hardwareControllerId}
     * Then a 200 status code is returned
     * And the HardwareController is returned
     */
    @Test
    void getHardwareController_whenGivenAValidHardwareControllerId_shouldReturnTheHardwareController() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //get the hardware controller
        result = mockMvc.perform(get("/hardwarecontroller/" + createdHardwareController.getId())
                        .content(hardwareControllerJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        HardwareController retrievedHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //check the values in the response
        assertEquals(hardwareController.getSerialNumber(), retrievedHardwareController.getSerialNumber());
        assertEquals(hardwareController.getName(), retrievedHardwareController.getName());
        assertEquals(createdHardwareController.getId(), retrievedHardwareController.getId());

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
        StandardError errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), StandardError.class);
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
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //get the hardware controller
        result = mockMvc.perform(get("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        List<HardwareController> retrievedHardwareControllers = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HardwareController>>() {
        });

        //check the values in the response
        assertEquals(1, retrievedHardwareControllers.size());
        assertEquals(hardwareController.getSerialNumber(), retrievedHardwareControllers.get(0).getSerialNumber());
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
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
        hardwareController.setName("Test Hardware Controller");
        Hardware hardware = new Hardware();
        hardware.setType("light");
        hardwareController.getHardware().add(hardware);
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();
        HardwareController createdHardwareController = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);

        //retrieve the hardware controller from the db
        HardwareControllerEntity hardwareControllerEntity = hardwareControllerRepository.findAll().get(0);

        //check the values in the response
        assertEquals(hardwareControllerEntity.getId(), createdHardwareController.getId());
        assertEquals(1, createdHardwareController.getHardware().size());
        assertEquals("light", createdHardwareController.getHardware().get(0).getType());
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

        //retrieve the hardware controller from the db
        HardwareControllerEntity hardwareControllerEntity = hardwareControllerRepository.findAll().get(0);

        //check the values in the response
        assertEquals(hardwareControllerEntity.getId(), createdHardwareController.getId());
        assertEquals(1, createdHardwareController.getSensors().size());
        assertEquals("temperature", createdHardwareController.getSensors().get(0).getSensorType());
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
        HardwareController hardwareController = new HardwareController();
        hardwareController.setSerialNumber("123456789");
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
}
