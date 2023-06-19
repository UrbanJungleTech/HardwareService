package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.HardwareControllerTestService;
import frentz.daniel.hardwareservice.HardwareTestService;
import frentz.daniel.hardwareservice.MqttTestService;
import frentz.daniel.hardwareservice.client.model.*;
import frentz.daniel.hardwareservice.config.mqtt.mockclient.StateChangeCallback;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.repository.TimerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HardwareEndpointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareRepository hardwareRepository;
    @Autowired
    private TimerRepository timerRepository;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;

    @Autowired
    private MqttTestService mqttTestService;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    StateChangeCallback stateChangeCallback;
    @Autowired
    HardwareTestService hardwareTestService;

    @BeforeEach
    void setUp() {
        this.hardwareControllerRepository.deleteAll();
        this.hardwareRepository.deleteAll();
        this.timerRepository.deleteAll();
    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When /hardware/{hardwareId} is called with the id from the Hardware
     * Then a 200 status code is returned
     * And the Hardware is returned
     * And the Hardware id matches the one in the database.
     */
    @Test
    void readHardware_whenGivenAValidHardwareId_shouldReturnTheHardware() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdHardware.getId()))
                .andExpect(jsonPath("$.type").value(createdHardware.getType()));
    }

    /**
     * Given an id which is not associated with a Hardware
     * When /hardware/{hardwareId} is called with the id
     * Then a 404 status code is returned
     * And the response body has 2 fields:
     * - httpStatus: 404
     * - message: "Hardware not found with id of {hardwareId}"
     */
    @Test
    void readHardware_whenGivenAnInvalidHardwareId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/hardware/123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpStatus").value(404))
                .andExpect(jsonPath("$.message").value("Hardware not found with id of 123"));
    }

    /**
     * Given an id which is associated with a Hardware which was created with a HardwareController via /hardwarecontroller/
     * When a DELETE request is made to /hardware/{hardwareId}
     * Then a 204 status code is returned
     * And the Hardware is deleted from the database
     */
    @Test
    public void deleteHardware_WhenGivenAValidHardwareId_shouldDeleteTheHardware() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        //retrieve the hardware controller from the db

        mockMvc.perform(delete("/hardware/" + createdHardware.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/hardware/" + createdHardware.getId()))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a PUT request is made to /hardware/{hardwareId} with a Hardware object and the following fields modified:
     * - type
     * - name
     * - port
     * Then a 200 status code is returned
     * And the Hardware is updated in the database
     * And the Hardware has the new values
     */
    @Test
    public void updateHardware_WhenGivenAValidHardwareId_shouldUpdateTheHardware() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        //retrieve the hardware controller from the db

        Hardware updatedHardware = new Hardware();
        updatedHardware.setType("heater");
        updatedHardware.setName("Updated Name");
        updatedHardware.setPort(2L);
        String updatedHardwareJson = objectMapper.writeValueAsString(updatedHardware);

        mockMvc.perform(put("/hardware/" + createdHardware.getId())
                        .content(updatedHardwareJson)
                        .contentType("application/json")
                        .content(updatedHardwareJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(updatedHardware.getType()))
                .andExpect(jsonPath("$.name").value(updatedHardware.getName()))
                .andExpect(jsonPath("$.port").value(updatedHardware.getPort()));

        HardwareEntity hardwareEntity = hardwareRepository.findAll().get(0);
        assertEquals(updatedHardware.getType(), hardwareEntity.getHardwareCategory());
        assertEquals(updatedHardware.getName(), hardwareEntity.getName());
        assertEquals(updatedHardware.getPort(), hardwareEntity.getPort());
    }

    /**
     * Given a Hardware has been created as part of a HardwareController via /hardwarecontroller/
     * When a POST request is made to /hardware/{hardwareId}/timer with a Timer object
     * Then a 201 status code is returned
     * And the Timer is returned in the response body
     * And the Timer is created in the database
     */
    @Test
    public void createTimer_WhenGivenAValidHardwareId_shouldCreateTheTimer() throws Exception {
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
        Hardware createdHardware = createdHardwareController.getHardware().get(0);

        //retrieve the hardware controller from the db

        Timer timer = new Timer();
        timer.setOnLevel(100);
        timer.setOnCronString("0 0 0 1 1 ? 2099");
        timer.setOffCronString("1 0 0 1 1 ? 2099");

        String timerJson = objectMapper.writeValueAsString(timer);

        MvcResult mvcResultTimer = mockMvc.perform(post("/hardware/" + createdHardware.getId() + "/timer")
                        .content(timerJson)
                        .contentType("application/json")
                        .content(timerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.onLevel").value(timer.getOnLevel()))
                .andExpect(jsonPath("$.onCronString").value(timer.getOnCronString()))
                .andExpect(jsonPath("$.offCronString").value(timer.getOffCronString()))
                .andReturn();

        Timer createdTimer = objectMapper.readValue(mvcResultTimer.getResponse().getContentAsString(), Timer.class);

        HardwareEntity hardwareEntity = hardwareRepository.findAll().get(0);
        assertEquals(1, hardwareEntity.getTimers().size());
        TimerEntity timerEntity = hardwareEntity.getTimers().get(0);
        assertEquals(createdTimer.getId(), timerEntity.getId());

    }

    /**
     * Given a hardware has been created as part of a hardware controller via /hardwarecontroller/
     * And a timer has been created for the hardware via /hardware/{hardwareId}/timer with:
     * - port = 1
     * And the timer has the on cron string "0/1 * * * * ?"
     * And the timer has the off cron string "0/2 * * * * ?"
     * Then after 2 seconds there should be at least 2 "on" states sent to the client over mqtt
     * and at least 1 "off" state sent to the client over mqtt
     */
    @Test
    public void createTimer_when2SecondsHavePassed_2OnEventsShouldHaveBeenSent_and1OffEventShouldHaveBeenSent() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setPort(1L);
        Timer timer = new Timer();
        timer.setOnLevel(100);
        timer.setOnCronString("0/3 * * * * ?");
        timer.setOffCronString("0/5 * * * * ?");
        hardware.getTimers().add(timer);
        HardwareController hardwareController = this.hardwareControllerTestService.addBasicHardwareControllerWithHardware(List.of(hardware));
        hardware = hardwareController.getHardware().get(0);

        Thread.sleep(10000);

        List<HardwareState> deliveredStates = stateChangeCallback.getStates(hardware.getPort());

        //count the on and off states saved
        int onCount = 0;
        int offCount = 0;
        for(HardwareState hardwareState : deliveredStates) {
            if(hardwareState.getState().equals(ONOFF.ON)) {
                onCount++;
            } else if(hardwareState.getState().equals(ONOFF.OFF)) {
                offCount++;
            }
        }
        System.out.println("onCount: " + onCount);
        System.out.println("offCount: " + offCount);
        //check how often the on and off states were sent, given that we're dealing with seconds
        // its necessary to give a little margin of error
        assertTrue(onCount >= 2 && onCount <= 3);
        assertTrue(offCount >= 1 && offCount <= 2);
    }
}
