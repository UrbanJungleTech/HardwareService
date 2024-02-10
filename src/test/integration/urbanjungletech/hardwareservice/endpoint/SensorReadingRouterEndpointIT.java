package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.helpers.mock.sensorreadingrouter.MockSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SensorReadingRouterEndpointIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Given a valid SensorReadingRouter object
     * When a POST request is made to /router/
     * Then the response should be 201 OK
     * And the response body should be the created SensorReadingRouter object with a non null id
     */
    @Test
    public void createBasicSensorReadingRouter() throws Exception {

        SensorReadingRouter router = new MockSensorReadingRouter();
        String routerJson = objectMapper.writeValueAsString(router);
        String sensorReadingRouterResponseJson = this.mockMvc.perform(post("/sensorreadingrouter/")
                        .contentType("application/json")
                .content(routerJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        SensorReadingRouter sensorReadingRouterResponse = objectMapper.readValue(sensorReadingRouterResponseJson, SensorReadingRouter.class);
        assertNotNull(sensorReadingRouterResponse.getId());
        this.mockMvc.perform(get("/sensorreadingrouter/" + sensorReadingRouterResponse.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Given a SensorReadingRouter has been created
     * When a PUT request is made to /router/{sensorReadingRouterId} and the updated SensorReadingRouter object has a sensor added
     * Then the response should be 200 OK
     * And the response body should be the updated SensorReadingRouter object with the added sensor
     */
    @Test
    public void updateSensorReadingRouterAddSensor() throws Exception {

    }

    /**
     * Given a sensorReadingRouterId which is associated with a SensorReadingRouter object
     * When a DELETE request is made to /router/{sensorReadingRouterId}
     * Then the response should be 204 NO CONTENT
     * And the SensorReadingRouter object should be deleted
     */
    @Test
    public void deleteRouter() throws Exception {
        SensorReadingRouter router = new MockSensorReadingRouter();
        String routerJson = objectMapper.writeValueAsString(router);
        String createdSensorReadingRouterJson = this.mockMvc.perform(post("/sensorreadingrouter/")
                        .contentType("application/json")
                        .content(routerJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        SensorReadingRouter createdSensorReadingRouter = objectMapper.readValue(createdSensorReadingRouterJson, SensorReadingRouter.class);
        this.mockMvc.perform(get("/sensorreadingrouter/" + createdSensorReadingRouter.getId()))
                .andExpect(status().isOk());
        this.mockMvc.perform(delete("/sensorreadingrouter/" + createdSensorReadingRouter.getId()))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/sensorreadingrouter/" + createdSensorReadingRouter.getId())).andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a SensorReadingRouter object
     * When a DELETE request is made to /router/{sensorReadingRouterId}
     * Then the response should be 404 NOT FOUND
     */
    @Test
    public void deleteRouterNotFound() throws Exception {
        this.mockMvc.perform(delete("/sensorreadingrouter/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a SensorReadingRouter object
     * When a GET request is made to /router/{sensorReadingRouterId}
     * Then the response should be 404 NOT FOUND
     */
    @Test
    public void getRouterNotFound() throws Exception {
        this.mockMvc.perform(get("/sensorreadingrouter/1"))
                .andExpect(status().isNotFound());
    }
}
