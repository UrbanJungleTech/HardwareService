package urbanjungletech.hardwareservice.sensorreadingrouter;

import com.azure.security.keyvault.secrets.SecretClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.KafkaSensorReadingRouter;
import urbanjungletech.hardwareservice.services.http.SensorTestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SensorReadingRouterIT {

    @Autowired
    private SecretClient secretClient;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SensorTestService sensorTestService;


    /**
     * Given a valid ScheduledSensorReading with a DatabaseSensorReadingRouter in its list of routers
     * When the ScheduledSensorReading is created via a POST to /scheduledreading
     * Then the response is 201
     * And the ScheduledSensorReading is returned
     * And the DatabaseSensorReadingRouter is returned in the list of routers
     */
    @Test
    void createScheduledReadingWithDatabaseSensorReadingRouter() throws Exception {

        Sensor sensor = this.sensorTestService.createBasicSensor().getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        DatabaseSensorReadingRouter databaseSensorReadingRouter = new DatabaseSensorReadingRouter();
        databaseSensorReadingRouter.setTableName("testTable");
        databaseSensorReadingRouter.setValueColumn("testValueColumn");
        databaseSensorReadingRouter.setTimestampColumn("testTimestampColumn");

        scheduledReading.getRouters().add(databaseSensorReadingRouter);

        String scheduledReadingResponseString = this.mockMvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ScheduledSensorReading scheduledReadingResponse = objectMapper.readValue(scheduledReadingResponseString, ScheduledSensorReading.class);
        DatabaseSensorReadingRouter databaseSensorReadingRouterResponse = (DatabaseSensorReadingRouter) scheduledReadingResponse.getRouters().get(0);

        assertSame(databaseSensorReadingRouter.getClass(), DatabaseSensorReadingRouter.class);
        assertEquals(databaseSensorReadingRouter.getType(), databaseSensorReadingRouterResponse.getType());
        assertEquals(scheduledReadingResponse.getId(), databaseSensorReadingRouterResponse.getScheduledSensorReadingId());


        assertEquals(databaseSensorReadingRouter.getTableName(), databaseSensorReadingRouterResponse.getTableName());
        assertEquals(databaseSensorReadingRouter.getValueColumn(), databaseSensorReadingRouterResponse.getValueColumn());
        assertEquals(databaseSensorReadingRouter.getTimestampColumn(), databaseSensorReadingRouterResponse.getTimestampColumn());

    }

    /**
     * Given a valid ScheduledSensorReading with a KafkaSensorReadingRouter in its list of routers
     * When the ScheduledSensorReading is created via a POST to /scheduledreading
     * Then the response is 201
     * And the ScheduledSensorReading is returned
     * And the KafkaSensorReadingRouter is returned in the list of routers
     */
    @Test
    void createScheduledReadingWithKafkaSensorReadingRouter() throws Exception {

        Sensor sensor = this.sensorTestService.createBasicSensor().getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0 0 0 1 1 ? 2099");
        KafkaSensorReadingRouter kafkaSensorReadingRouter = new KafkaSensorReadingRouter();

        scheduledReading.getRouters().add(kafkaSensorReadingRouter);

        String scheduledReadingResponseString = this.mockMvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ScheduledSensorReading scheduledReadingResponse = objectMapper.readValue(scheduledReadingResponseString, ScheduledSensorReading.class);
        KafkaSensorReadingRouter kafkaSensorReadingRouterResponse = (KafkaSensorReadingRouter) scheduledReadingResponse.getRouters().get(0);

        assertSame(kafkaSensorReadingRouterResponse.getClass(), KafkaSensorReadingRouter.class);
        assertEquals(kafkaSensorReadingRouterResponse.getType(), kafkaSensorReadingRouterResponse.getType());
        assertEquals(scheduledReadingResponse.getId(), kafkaSensorReadingRouterResponse.getScheduledSensorReadingId());
    }

    /**
     * Given a valid ScheduledSensorReading with an AzureQueueSensorReadingRouter in its list of routers
     * When the ScheduledSensorReading is created via a POST to /scheduledreading
     * Then the response is 201
     * And the ScheduledSensorReading is returned
     * And the ScheduledSensorReading is returned in the list of routers
     * And its credentials are returned
     */
    @Test
    void createScheduledReadingWithAzureQueueSensorReadingRouter() throws Exception {
        Sensor sensor = this.sensorTestService.createBasicSensor().getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");
        AzureQueueSensorReadingRouter azureQueueSensorReadingRouter = new AzureQueueSensorReadingRouter();
        azureQueueSensorReadingRouter.setQueueName("testQueueName");
        TokenCredentials credentials = new TokenCredentials();
        credentials.setUrl("");
        credentials.setTokenValue("");
        azureQueueSensorReadingRouter.setCredentials(credentials);
        scheduledReading.getRouters().add(azureQueueSensorReadingRouter);


        String scheduledReadingResponseString = this.mockMvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ScheduledSensorReading scheduledReadingResponse = objectMapper.readValue(scheduledReadingResponseString, ScheduledSensorReading.class);
        AzureQueueSensorReadingRouter azureQueueSensorReadingRouterResponse = (AzureQueueSensorReadingRouter) scheduledReadingResponse.getRouters().get(0);
        assertNotNull(azureQueueSensorReadingRouterResponse.getCredentials());

        assertSame(azureQueueSensorReadingRouterResponse.getClass(), AzureQueueSensorReadingRouter.class);
        Credentials returnedCredentials = azureQueueSensorReadingRouterResponse.getCredentials();
        assertSame(returnedCredentials.getClass(), TokenCredentials.class);
        TokenCredentials returnedTokenCredentials = (TokenCredentials) azureQueueSensorReadingRouterResponse.getCredentials();

        assertEquals(azureQueueSensorReadingRouter.getType(), azureQueueSensorReadingRouterResponse.getType());
        assertEquals(scheduledReadingResponse.getId(), azureQueueSensorReadingRouterResponse.getScheduledSensorReadingId());
        assertNotNull(azureQueueSensorReadingRouterResponse.getId());

        //Check that the credentials are stored in azure correctly
        assertEquals(credentials.getTokenValue(), secretClient.getSecret(returnedTokenCredentials.getTokenValue()).getValue());
        assertEquals(credentials.getUrl(), secretClient.getSecret(returnedTokenCredentials.getUrl()).getValue());

        Thread.sleep(10000);
    }
}
