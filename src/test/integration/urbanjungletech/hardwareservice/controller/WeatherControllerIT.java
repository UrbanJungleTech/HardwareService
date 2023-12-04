package urbanjungletech.hardwareservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.services.config.WeatherProperties;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WeatherControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    WeatherProperties weatherProperties;


    /**
     * Given a controller of type weather with a single sensor of type temperature
     * Given the controller has been created via a POST request to /hardwarecontroller/
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then the response status is 200
     * And the response body contains the sensor reading
     */
    @Test
    public void getSensorReading_temperatureSensor_returnsDoubleGreaterThanZero() throws Exception {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("weather");
        TokenCredentials tokenCredentials = new TokenCredentials();
        tokenCredentials.setTokenValue(weatherProperties.getApikey());
        tokenCredentials.setUrl("https://api.tomorrow.io/v4/timelines");
        hardwareController.setCredentials(tokenCredentials);
        Sensor temperatureSensor = new Sensor();
        temperatureSensor.setName("Temperature Sensor");
        temperatureSensor.getConfiguration().put("sensorType", "temperature");
        temperatureSensor.getConfiguration().put("latitude", "43.64493");
        temperatureSensor.getConfiguration().put("longitude", "-79.39076");
        Sensor humiditySensor = new Sensor();
        humiditySensor.setName("Humidity Sensor");
        humiditySensor.getConfiguration().put("sensorType", "humidity");
        humiditySensor.getConfiguration().put("latitude", "43.64493");
        humiditySensor.getConfiguration().put("longitude", "-79.39076");
        hardwareController.getSensors().add(humiditySensor);
        hardwareController.getSensors().add(temperatureSensor);
        String controllerResponseString = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        HardwareController controllerResponse = objectMapper.readValue(controllerResponseString, HardwareController.class);

        //test the temperature
        Sensor temperatureSensorResponse = controllerResponse.getSensors().get(1);
        String sensorReadingResponse = this.mockMvc.perform(get("/sensor/" + temperatureSensorResponse.getId() + "/reading"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReading sensorReading = objectMapper.readValue(sensorReadingResponse, SensorReading.class);
        Double temperatureReading = sensorReading.getReading();
        assertNotNull(temperatureReading);
        assertNotEquals(0, temperatureReading);
        System.out.println("Temperature: " + temperatureReading);

        //test the humidity
        Sensor humiditySensorResponse = controllerResponse.getSensors().get(0);
        String humiditySensorReadingResponse = this.mockMvc.perform(get("/sensor/" + humiditySensorResponse.getId() + "/reading"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReading humiditySensorReading = objectMapper.readValue(humiditySensorReadingResponse, SensorReading.class);
        Double humidityReading = humiditySensorReading.getReading();
        assertNotNull(humidityReading);
        assertNotEquals(0, humidityReading);
        System.out.println("Humidity: " + humidityReading);
    }
}
