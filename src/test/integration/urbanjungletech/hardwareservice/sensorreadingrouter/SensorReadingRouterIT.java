package urbanjungletech.hardwareservice.sensorreadingrouter;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.helpers.services.config.AzureProperties;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.helpers.services.http.SensorTestService;
import urbanjungletech.hardwareservice.helpers.services.router.DatabaseRouterHelperService;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.KafkaSensorReadingRouter;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
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
    @Autowired
    private AzureProperties azureProperties;
    @Autowired
    private DatabaseRouterHelperService databaseRouterHelperService;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;


    /**
     * Given a valid ScheduledSensorReading with a DatabaseSensorReadingRouter in its list of routers
     * When the ScheduledSensorReading is created via a POST to /scheduledreading
     * Then the response is 201
     * And the ScheduledSensorReading is returned
     * And the DatabaseSensorReadingRouter is returned in the list of routers
     */
    @Test
    void createScheduledReadingWithDatabaseSensorReadingRouter() throws Exception {

        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        hardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor sensor = hardwareController.getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        DatabaseSensorReadingRouter databaseSensorReadingRouter = databaseRouterHelperService.getDatabaseRouter();
        scheduledReading.getRouters().add(databaseSensorReadingRouter);

        DataSource dataSource = DataSourceBuilder.create()
                .url(databaseSensorReadingRouter.getCredentials().getHost())
                .username(databaseSensorReadingRouter.getCredentials().getUsername())
                .password(databaseSensorReadingRouter.getCredentials().getPassword())
                .driverClassName(databaseSensorReadingRouter.getCredentials().getDriver())
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //create the table in the database
        jdbcTemplate.execute("CREATE TABLE " + databaseSensorReadingRouter.getTableName() + " (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "READING DOUBLE, " +
                "READINGTIME TIMESTAMP," +
                "SENSORID BIGINT)");



        scheduledReading.setCronString("0/1 * * * * ?");

        this.mockMvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated());


        await().atMost(50, TimeUnit.SECONDS).untilAsserted(() -> {
            List<SensorReading> sensorReadings = jdbcTemplate.query("SELECT * FROM " + databaseSensorReadingRouter.getTableName(),
                    (resultSet, i) -> {
                        SensorReading sensorReading = new SensorReading();
                        return sensorReading;
                    });
            assertNotEquals(0, sensorReadings.size());
        });
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

        Sensor sensor = this.sensorTestService.createMqttSensor().getSensors().get(0);

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

        //make sure we start with a clean queue.
        QueueClient queueClient = new QueueClientBuilder()
                .endpoint(azureProperties.getStorageQueue().getQueueName())
                .sasToken(azureProperties.getStorageQueue().getKey())
                .buildClient();
        queueClient.clearMessages();

        Sensor sensor = this.sensorTestService.createMqttSensor().getSensors().get(0);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");
        AzureQueueSensorReadingRouter azureQueueSensorReadingRouter = new AzureQueueSensorReadingRouter();
        azureQueueSensorReadingRouter.setQueueName("testQueueName");
        TokenCredentials credentials = new TokenCredentials();
        credentials.setUrl(azureProperties.getStorageQueue().getQueueName());
        credentials.setTokenValue(azureProperties.getStorageQueue().getKey());
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

        TokenCredentials returnedTokenCredentials = (TokenCredentials) azureQueueSensorReadingRouterResponse.getCredentials();

        assertEquals(azureQueueSensorReadingRouter.getType(), azureQueueSensorReadingRouterResponse.getType());
        assertEquals(scheduledReadingResponse.getId(), azureQueueSensorReadingRouterResponse.getScheduledSensorReadingId());
        assertNotNull(azureQueueSensorReadingRouterResponse.getId());

        //Check that the credentials are stored in azure correctly
        assertEquals(credentials.getTokenValue(), secretClient.getSecret(returnedTokenCredentials.getTokenValue()).getValue());
        assertEquals(credentials.getUrl(), secretClient.getSecret(returnedTokenCredentials.getUrl()).getValue());


        //Check that the message is sent to the queue and check the contents
        await().atMost(10, TimeUnit.SECONDS).until(() -> queueClient.peekMessage() != null);

        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
        String messageBody = queueMessageItem.getBody().toString();
        SensorReading sensorReading = objectMapper.readValue(messageBody, SensorReading.class);
        assertEquals(sensorReading.getSensorId(), sensor.getId());
        assertEquals(1.0, sensorReading.getReading());
    }
}
