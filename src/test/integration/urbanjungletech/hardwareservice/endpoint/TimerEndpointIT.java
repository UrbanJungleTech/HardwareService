package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.TimerRepository;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "development.mqtt.client.enabled=false",
        "mqtt.server.enabled=false",
        "mqtt-rpc.enabled=false"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TimerEndpointIT {

    @Autowired
    TimerRepository timerRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;

    @Autowired
    @Qualifier("HardwareScheduler")
    private Scheduler scheduler;

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a timer has been added to the Hardware via a POST request to /hardware/{hardwareId}/timer
     * Then the timer can be retrieved via a GET request to /timer/{timerId}
     */
    @Test
    public void testGetTimer() throws Exception {
        HardwareController hardwareController = hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardwareController.getHardware().add(hardware);
        hardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        hardware = hardwareController.getHardware().get(0);
        Timer timer = new Timer();
        timer.setLevel(100);
        timer.setState("on");
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
        String timerJsonResponse = this.mockMvc.perform(get("/timer/" + timerId))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Timer responseTimer = objectMapper.readValue(timerJsonResponse, Timer.class);
        assertEquals(createdTimer.getId(), responseTimer.getId());
        assertEquals(createdTimer.getHardwareId(), responseTimer.getHardwareId());
        assertEquals(createdTimer.getCronString(), responseTimer.getCronString());
        assertEquals(createdTimer.getLevel(), responseTimer.getLevel());
        assertEquals(createdTimer.getState(), responseTimer.getState());

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
    public void testDeleteTimerShouldDeleteTheTimerEntity() throws Exception {
        HardwareController hardwareController = hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardware.setOffState("off");
        Timer timer = new Timer();
        timer.setLevel(100);
        timer.setCronString("0 0 0 1 1 ? 2099");
        hardware.getTimers().add(timer);
        hardwareController.getHardware().add(hardware);
        HardwareController createdHardwareController = hardwareControllerTestService.postHardwareController(hardwareController);
        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        Timer createdTimer = createdHardware.getTimers().get(0);

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
    public void testDeleteTimerNotFoundShouldReturn404() throws Exception {
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

        HardwareController hardwareController = this.hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardwareController.getHardware().add(hardware);
        hardwareController.getHardware().get(0).getTimers().add(timer1);
        hardwareController.getHardware().get(0).getTimers().add(timer2);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        Timer createdTimer1 = createdHardwareController.getHardware().get(0).getTimers().get(0);
        Timer createdTimer2 = createdHardwareController.getHardware().get(0).getTimers().get(1);
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
        HardwareController hardwareController = hardwareControllerTestService.createMockHardwareController();
        Hardware hardware = new Hardware();
        hardwareController.getHardware().add(hardware);
        Timer timer = new Timer();
        timer.setLevel(100);
        timer.setCronString("0 0 0 1 1 ? 2099");
        hardware.getTimers().add(timer);
        HardwareController createdHardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        Hardware createdHardware = createdHardwareController.getHardware().get(0);
        Timer updatedTimer = createdHardware.getTimers().get(0);
        long timerId = updatedTimer.getId();
        updatedTimer.setLevel(50);
        updatedTimer.setCronString("0 0 0 1 1 ? 2011");
        String timerJson = objectMapper.writeValueAsString(updatedTimer);
        String responseJson = this.mockMvc.perform(put("/timer/" + timerId)
                        .contentType("application/json")
                        .content(timerJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Timer responseTimer = objectMapper.readValue(responseJson, Timer.class);
        assertEquals(updatedTimer.getLevel(), responseTimer.getLevel());
        assertEquals(updatedTimer.getCronString(), responseTimer.getCronString());

        responseJson = this.mockMvc.perform(get("/timer/" + timerId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        responseTimer = objectMapper.readValue(responseJson, Timer.class);
        assertEquals(updatedTimer.getLevel(), responseTimer.getLevel());
        assertEquals(updatedTimer.getCronString(), responseTimer.getCronString());

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

    /**
     * Given a Hardware has been created with a timer
     * When the timer is updated
     * Then the schedule exists with the key represented by the timer id
     * and the schedule is updated with the new cron string
     */
    @Test
    public void testSchedulerIsUpdatedWithUpdatedCronExpression () throws Exception {
        HardwareController createdHardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        Hardware hardware = createdHardwareController.getHardware().get(0);
        Timer timer = new Timer();
        timer.setCronString("0 0 0 1 1 ? 2099");
        hardware.getTimers().add(timer);
        createdHardwareController = this.hardwareControllerTestService.postHardwareController(createdHardwareController);
        Timer createdTimer = createdHardwareController.getHardware().get(0).getTimers().get(0);
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(this.scheduler.checkExists(new TriggerKey(String.valueOf(createdTimer.getId())))).isTrue();
        });
        String updatedCronString = "0 0 0 1 1 ? 2098";
        createdTimer.setCronString(updatedCronString);
        this.hardwareControllerTestService.putHardwareController(createdHardwareController);
        await().atMost(15, TimeUnit.SECONDS).untilAsserted(() -> {
            TriggerKey triggerKey = new TriggerKey(String.valueOf(createdTimer.getId()));
            assertThat(this.scheduler.checkExists(triggerKey)).isTrue();
            //create a Date to represent the next expected fire date
            Date nextExpectedFireDate = Date.valueOf("2098-01-01");
            assertEquals(nextExpectedFireDate, this.scheduler.getTrigger(triggerKey).getNextFireTime());
        });

    }

    /**
     * Given a Hardware has been created with a timer
     * When the hardware is deleted
     * Then the timer is deleted
     * and the schedular stops the job
     */
    @Test
    public void testDeleteHardwareDeletesTheAssociatedSchedule() throws Exception {
        HardwareController createdHardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        Hardware hardware = createdHardwareController.getHardware().get(0);
        Timer timer = new Timer();
        timer.setCronString("0 0 0 1 1 ? 2099");
        hardware.getTimers().add(timer);
        createdHardwareController = this.hardwareControllerTestService.postHardwareController(createdHardwareController);
        Timer createdTimer = createdHardwareController.getHardware().get(0).getTimers().get(0);
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(this.scheduler.checkExists(new TriggerKey(String.valueOf(createdTimer.getId())))).isTrue();
        });
        this.hardwareControllerTestService.deleteHardwareController(createdHardwareController.getId());
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(this.scheduler.checkExists(new TriggerKey(String.valueOf(createdTimer.getId())))).isFalse();
        });
    }

    /**
     * Given a Hardware has been created with a timer
     * Then the schedule exists with the key represented by the timer id
     */
    @Test
    public void testCreateHardwareCreatesTheAssociatedSchedule() throws Exception {
        HardwareController createdHardwareController = this.hardwareControllerTestService.createMockHardwareControllerWithDefaultHardware();
        Hardware hardware = createdHardwareController.getHardware().get(0);
        Timer timer = new Timer();
        timer.setCronString("0 0 0 1 1 ? 2099");
        hardware.getTimers().add(timer);
        createdHardwareController = this.hardwareControllerTestService.postHardwareController(createdHardwareController);
        Timer createdTimer = createdHardwareController.getHardware().get(0).getTimers().get(0);
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(this.scheduler.checkExists(new TriggerKey(String.valueOf(createdTimer.getId())))).isTrue();
        });
    }
}
