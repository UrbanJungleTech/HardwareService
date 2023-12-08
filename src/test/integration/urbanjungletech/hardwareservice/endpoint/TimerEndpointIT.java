package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.TimerRepository;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;
import urbanjungletech.hardwareservice.schedule.sensor.SensorScheduleService;
import urbanjungletech.hardwareservice.services.http.HardwareTestService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    @Autowired
    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    @Autowired
    private SensorScheduleService sensorScheduleService;

    @BeforeEach
    public void setup() throws SchedulerException {
        hardwareControllerRepository.deleteAll();
        hardwareControllerRepository.flush();
        this.sensorScheduleService.deleteAll();
        this.scheduledHardwareScheduleService.deleteAllSchedules();
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
        timer.setLevel(100);
        timer.setCronString("0 0 0 1 1 ? 2099");
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
        timer1.setCronString("0 0 0 1 1 ? 2099");
        Timer timer2 = new Timer();
        timer2.setCronString("0 0 0 1 1 ? 2099");

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
        Timer updatedTimer = hardware.getTimers().get(0);
        long timerId = updatedTimer.getId();
        updatedTimer.setLevel(50);
        updatedTimer.setCronString("0 0 0 1 1 ? 2011");
        String timerJson = objectMapper.writeValueAsString(updatedTimer);
        this.mockMvc.perform(put("/timer/" + timerId)
                        .contentType("application/json")
                        .content(timerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(timerId))
                .andExpect(jsonPath("$.onLevel").value(50))
                .andExpect(jsonPath("$.cronString").value(updatedTimer.getCronString()));

        this.mockMvc.perform(get("/timer/" + timerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(timerId))
                .andExpect(jsonPath("$.onLevel").value(updatedTimer.getLevel()))
                .andExpect(jsonPath("$.onCronString").value(updatedTimer.getCronString()));
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
