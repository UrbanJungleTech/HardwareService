package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.KafkaSensorReadingRouter;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.services.http.ScheduledSensorReadingTestService;
import urbanjungletech.hardwareservice.services.http.SensorTestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
    ObjectMapper objectMapper;
    @Autowired
    HardwareControllerRepository hardwareControllerRepository;

    @Autowired
    ScheduledSensorReadingTestService scheduledSensorReadingTestService;

    @Autowired
    SensorTestService sensorTestService;


    /**
     * Given a valid ScheduledSensorReading
     * When the ScheduledSensorReading is created via a POST to /sensor/{sensorId}/scheduledreading
     * Then the response is 201
     * And the ScheduledSensorReading is returned
     * And the ScheduledSensorReading's id is not null
     * And the ScheduledSensorReading's sensorId matches the sensorId in the path
     * And the ScheduledSensorReading's cronString matches the cronString in the request body
     */
    @Test
    void createScheduledReading() throws Exception {
        Sensor sensor = this.sensorTestService.createBasicSensor().getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0 0 0 1 1 ? 2099");

        String scheduledReadingResponseString = this.mockmvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ScheduledSensorReading scheduledReadingResponse = objectMapper.readValue(scheduledReadingResponseString, ScheduledSensorReading.class);

        assertEquals(scheduledReadingResponse.getId(), scheduledReadingResponse.getId());
        assertEquals(scheduledReadingResponse.getSensorId(), sensor.getId());
        assertEquals(scheduledReadingResponse.getCronString(), scheduledReading.getCronString());
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
}
