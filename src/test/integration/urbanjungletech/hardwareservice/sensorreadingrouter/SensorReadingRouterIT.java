package urbanjungletech.hardwareservice.sensorreadingrouter;

import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.common.sas.AccountSasPermission;
import com.azure.storage.common.sas.AccountSasResourceType;
import com.azure.storage.common.sas.AccountSasService;
import com.azure.storage.common.sas.AccountSasSignatureValues;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
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
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SensorReadingRouterIT {

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

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        DatabaseSensorReadingRouter databaseSensorReadingRouter = databaseRouterHelperService.getDatabaseRouter();

        DatabaseCredentials credentials = (DatabaseCredentials) databaseSensorReadingRouter.getDatabaseConnectionDetails().getCredentials();
        DatabaseConnectionDetails databaseConnectionDetails = databaseSensorReadingRouter.getDatabaseConnectionDetails();
        DataSource dataSource = DataSourceBuilder.create()
                .url(databaseConnectionDetails.getUrl())
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .driverClassName(databaseConnectionDetails.getDriver())
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //create the table in the database
        jdbcTemplate.execute("CREATE TABLE " + databaseSensorReadingRouter.getTableName() + " (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "READING DOUBLE, " +
                "READINGTIME TIMESTAMP," +
                "SENSORID BIGINT)");
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        sensor.getSensorReadingRouters().add(databaseSensorReadingRouter);
        hardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);


        scheduledReading.setCronString("0/1 * * * * ?");
        sensor = hardwareController.getSensors().get(0);
        sensor.getScheduledSensorReadings().add(scheduledReading);
        this.mockMvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated());


        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            List<SensorReading> sensorReadings = jdbcTemplate.query("SELECT * FROM " + databaseSensorReadingRouter.getTableName(),
                    (resultSet, i) -> {
                        SensorReading sensorReading = new SensorReading();
                        return sensorReading;
                    });
            assertNotEquals(0, sensorReadings.size());
        });
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
        QueueServiceClient queueServiceClient = new QueueServiceClientBuilder()
                .endpoint(azureProperties.getStorageQueue().getEndpoint() + azureProperties.getStorageQueue().getStorageAccountName())
                .credential(new StorageSharedKeyCredential(azureProperties.getStorageQueue().getStorageAccountName(), azureProperties.getStorageQueue().getStorageAccountKey()))
                .buildClient();
        queueServiceClient.createQueue("myqueue");
        AccountSasSignatureValues accountSasSignatureValues = new AccountSasSignatureValues(
                java.time.OffsetDateTime.now().plusDays(1),
                new AccountSasPermission().setWritePermission(true)
                        .setCreatePermission(true)
                        .setAddPermission(true),
                new AccountSasService().setQueueAccess(true),
                new AccountSasResourceType().setService(true)
                        .setObject(true)
                        .setObject(true));
        String sasToken = queueServiceClient.generateAccountSas(accountSasSignatureValues);
        this.azureProperties.getStorageQueue().setKey(sasToken);

        QueueClient queueClient = new QueueClientBuilder()
                .endpoint(azureProperties.getStorageQueue().getEndpoint() + azureProperties.getStorageQueue().getStorageAccountName())
                .credential(new StorageSharedKeyCredential(azureProperties.getStorageQueue().getStorageAccountName(), azureProperties.getStorageQueue().getStorageAccountKey()))
                .queueName("myqueue")
                .buildClient();
        queueClient.clearMessages();


        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();

        AzureQueueSensorReadingRouter azureQueueSensorReadingRouter = new AzureQueueSensorReadingRouter();
        azureQueueSensorReadingRouter.setQueueName("testQueueName");
        TokenCredentials credentials = new TokenCredentials();
        AzureConnectionDetails azureConnectionDetails = new AzureConnectionDetails();
        azureConnectionDetails.setUrl(azureProperties.getStorageQueue().getEndpoint() + azureProperties.getStorageQueue().getQueueName());
        credentials.setTokenValue(sasToken);
        azureConnectionDetails.setCredentials(credentials);
        azureQueueSensorReadingRouter.setAzureConnectionDetails(azureConnectionDetails);

        hardwareController.getSensors().get(0).getSensorReadingRouters().add(azureQueueSensorReadingRouter);

        hardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);

        ScheduledSensorReading scheduledReading = new ScheduledSensorReading();
        scheduledReading.setCronString("0/1 * * * * ?");
        this.mockMvc.perform(post("/sensor/" + hardwareController.getSensors().get(0).getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated());


        Sensor sensor = hardwareController.getSensors().get(0);
        this.mockMvc.perform(post("/sensor/" + sensor.getId() + "/scheduledreading")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(scheduledReading)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        //Check that the message is sent to the queue and check the contents
        await().atMost(10, TimeUnit.SECONDS).until(() -> queueClient.peekMessage() != null);

        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
        String messageBody = queueMessageItem.getBody().toString();
        SensorReading sensorReading = objectMapper.readValue(messageBody, SensorReading.class);
        assertEquals(sensorReading.getSensorId(), sensor.getId());
        assertEquals(1.0, sensorReading.getReading());
    }
}
