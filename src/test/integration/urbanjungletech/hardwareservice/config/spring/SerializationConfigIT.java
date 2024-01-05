package urbanjungletech.hardwareservice.config.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller.MockHardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the serialization configuration, intended to ensure that each of the HardwareController
 * implementations can be deserialized from JSON based on their type field.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({MockHardwareController.class})
public class SerializationConfigIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    /**
     * Given a JSON string representing a MqttHardwareController
     * When the JSON string is deserialized
     * Then the deserialized object should be a MqttHardwareController
     */
    @Test
    public void testDeserializationMqttController() throws JsonProcessingException {
        String json = "{\"type\":\"MqttHardwareController\"}";
        HardwareController controller = objectMapper.readValue(json, HardwareController.class);
        assertEquals(MqttHardwareController.class, controller.getClass());
    }

    /**
     * Given a JSON string representing a MockHardwareController
     * When the JSON string is deserialized
     * Then the deserialized object should be a MockHardwareController
     */
    @Test
    public void testDeserializationMockController() throws JsonProcessingException {
        String json = "{\"type\":\"MockHardwareController\"}";
        HardwareController controller = objectMapper.readValue(json, HardwareController.class);
        assertEquals(MockHardwareController.class, controller.getClass());
    }

    /**
     * Given a JSON string representing a WeatherHardwareController
     * When the JSON string is deserialized
     * Then the deserialized object should be a WeatherHardwareController
     */
    @Test
    public void testDeserializationWeatherController() throws JsonProcessingException {
        String json = "{\"type\":\"WeatherHardwareController\"}";
        HardwareController controller = objectMapper.readValue(json, HardwareController.class);
        assertEquals(WeatherHardwareController.class, controller.getClass());
    }

    /**
     * Given a JSON string representing a CpuHardwareController
     * When the JSON string is deserialized
     * Then the deserialized object should be a CpuHardwareController
     */
    @Test
    public void testDeserializationCpuController() throws JsonProcessingException {
        String json = "{\"type\":\"CpuHardwareController\"}";
        HardwareController controller = objectMapper.readValue(json, HardwareController.class);
        assertEquals(CpuHardwareController.class, controller.getClass());
    }

    /**
     * Given a JSON string representing a TpLinkHardwareController
     * When the JSON string is deserialized
     * Then the deserialized object should be a TpLinkHardwareController
     */
    @Test
    public void testDeserializationTPLinkController() throws JsonProcessingException {
        String json = "{\"type\":\"TpLinkHardwareController\"}";
        HardwareController controller = objectMapper.readValue(json, HardwareController.class);
        assertEquals(TpLinkHardwareController.class, controller.getClass());
    }
}
