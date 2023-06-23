package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.HardwareTestService;
import frentz.daniel.hardwareservice.addition.TimerAdditionService;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Timer;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.TimerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TimerEndpointIT {

    @Autowired
    TimerRepository timerRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private HardwareTestService hardwareTestService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;

    @BeforeEach
    public void setup() {
        timerRepository.deleteAll();
        hardwareControllerRepository.deleteAll();
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a timer has been added to the Hardware via a POST request to /hardware/{hardwareId}/timer
     * Then the timer can be retrieved via a GET request to /timer/{timerId}
     */
    @Test
    public void testGetTimer() throws Exception {
        HardwareController hardwareController = hardwareTestService.createBasicHardware();
        Hardware hardware = hardwareController.getHardware().get(0);
        Timer timer = new Timer();
        timer.setOnLevel(100);
        timer.setOnCronString("0 0 0 1 1 ? 2099");
        timer.setOffCronString("1 0 0 1 1 ? 2099");
        hardware.getTimers().add(timer);
        String timerJson = objectMapper.writeValueAsString(timer);
        MvcResult timerResult = this.mockMvc.perform(post("/hardware/" + hardware.getId() + "/timer")
                        .contentType("application/json")
                        .content(timerJson))
                .andExpect(status().isCreated())
                .andReturn();
        Timer createdTimer = objectMapper.readValue(timerResult.getResponse().getContentAsString(), Timer.class);
        long timerId = createdTimer.getId();
        this.mockMvc.perform(get("/timer/" + timerId))
                .andExpect(status().isOk());
    }

    /**
     * Given a timer id which is not associated with a timer
     * When a call is made to /timer/{timerId}
     * Then a 404 is returned
     */

    @Test
    public void testGetTimerNotFound() throws Exception {
        this.mockMvc.perform(get("/timer/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a timer has been added to the Hardware via a POST request to /hardware/{hardwareId}/timer
     * When a Delete request is sent to /timer/{timerId}
     * Then the timer can no longer be retrieved via a GET request to /timer/{timerId}
     */
    @Test
    public void testDeleteTimer() throws Exception {
        HardwareController hardwareController = hardwareTestService.createBasicHardwareWithTimer();
        Hardware hardware = hardwareController.getHardware().get(0);
        Timer createdTimer = hardware.getTimers().get(0);

        long timerId = createdTimer.getId();
        this.mockMvc.perform(delete("/timer/" + timerId))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/timer/" + timerId))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a timer id which is not associated with a timer
     * When a DELETE call is made to /timer/{timerId}
     * Then a 404 is returned
     */
    @Test
    public void testDeleteTimerNotFound() throws Exception {
        this.mockMvc.perform(delete("/timer/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given two HardwareControllers have been created via the endpoint /hardwarecontroller with a single hardware and a timer
     * When a call is made to /timer
     * Then a list of all timers is returned
     */
    @Test
    public void testGetAllTimers() throws Exception {
        Timer timer1 = new Timer();
        timer1.setOnCronString("0 0 0 1 1 ? 2099");
        timer1.setOffCronString("1 0 0 1 1 ? 2099");
        Timer timer2 = new Timer();
        timer2.setOnCronString("0 0 0 1 1 ? 2099");
        timer2.setOffCronString("1 0 0 1 1 ? 2099");

        HardwareController hardwareController = hardwareTestService.createBasicHardwareWithTimers(List.of(timer1, timer2));
        Timer createdTimer1 = hardwareController.getHardware().get(0).getTimers().get(0);
        Timer createdTimer2 = hardwareController.getHardware().get(0).getTimers().get(1);
        this.mockMvc.perform(get("/timer/"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String contentAsString = result.getResponse().getContentAsString();
                    Timer[] timers = objectMapper.readValue(contentAsString, Timer[].class);
                    assert (timers.length == 2);
                    assert (timers[0].getId() == createdTimer1.getId() || timers[1].getId() == createdTimer1.getId());
                    assert (timers[0].getId() == createdTimer2.getId() || timers[1].getId() == createdTimer2.getId());
                });
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware with a single timer
     * When a PUT request is sent to /timer/{timerId}
     * Then the timer can be retrieved via a GET request to /timer/{timerId} with the updated values
     */
    @Test
    public void testUpdateTimer() throws Exception {
        HardwareController hardwareController = hardwareTestService.createBasicHardwareWithTimer();
        Hardware hardware = hardwareController.getHardware().get(0);
        Timer createdTimer = hardware.getTimers().get(0);
        long timerId = createdTimer.getId();
        createdTimer.setOnLevel(50);
        createdTimer.setOnCronString("0 0 0 1 1 ? 2011");
        createdTimer.setOffCronString("1 0 0 1 1 ? 2011");
        String timerJson = objectMapper.writeValueAsString(createdTimer);
        this.mockMvc.perform(put("/timer/" + timerId)
                        .contentType("application/json")
                        .content(timerJson))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/timer/" + timerId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String contentAsString = result.getResponse().getContentAsString();
                    Timer timer = objectMapper.readValue(contentAsString, Timer.class);
                    assertEquals(createdTimer.getOnLevel(), timer.getOnLevel());
                    assertEquals(createdTimer.getOnCronString(), timer.getOnCronString());
                    assertEquals(createdTimer.getOffCronString(), timer.getOffCronString());
                });
    }

    /**
     * Given a timer id which is not associated with a timer
     * When a PUT call is made to /timer/{timerId}
     * Then a 404 is returned
     */
    @Test
    public void testUpdateTimerNotFound() throws Exception {
        Timer timer = new Timer();
        String timerJson = objectMapper.writeValueAsString(timer);
        this.mockMvc.perform(put("/timer/1")
                        .contentType("application/json")
                        .content(timerJson))
                .andExpect(status().isNotFound());
    }

}
