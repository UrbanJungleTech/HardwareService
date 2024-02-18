package urbanjungletech.hardwareservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.exception.model.InvalidRequestError;
import urbanjungletech.hardwareservice.helpers.WeatherTestService;
import urbanjungletech.hardwareservice.helpers.services.config.WeatherProperties;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.sensor.WeatherSensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model.WeatherSensorTypes;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @Autowired
    WeatherTestService weatherTestService;
    @Autowired
    HardwareControllerTestService hardwareControllerTestService;


    /**
     * Given a controller of type weather with a single sensor of type temperature
     * Given the controller has been created via a POST request to /hardwarecontroller/
     * When a GET request is made to /sensor/{sensorId}/reading
     * Then the response status is 200
     * And the response body contains the sensor reading
     */
    @Test
    public void getSensorReading_temperatureSensor_returnsDoubleGreaterThanZero() throws Exception {
        WeatherHardwareController hardwareController = this.weatherTestService.createWeatherHardwareController();
        WeatherSensor temperatureSensor = this.weatherTestService.createWeatherSensor();
        temperatureSensor.setPort(WeatherSensorTypes.TEMPERATURE.toString());
        hardwareController.getSensors().add(temperatureSensor);
        WeatherSensor humiditySensor = this.weatherTestService.createWeatherSensor();
        humiditySensor.setPort(WeatherSensorTypes.HUMIDITY.toString());
        hardwareController.getSensors().add(humiditySensor);
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

        //test the humidity
        Sensor humiditySensorResponse = controllerResponse.getSensors().get(0);
        String humiditySensorReadingResponse = this.mockMvc.perform(get("/sensor/" + humiditySensorResponse.getId() + "/reading"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SensorReading humiditySensorReading = objectMapper.readValue(humiditySensorReadingResponse, SensorReading.class);
        Double humidityReading = humiditySensorReading.getReading();
        assertNotNull(humidityReading);
        assertNotEquals(0, humidityReading);
    }

    /**
     * Given a controller of type weather with a port equal to null
     * When a POST request is made to /hardwarecontroller/
     * Then the response status is 400
     * And the response body contains an error message
     */
    @Test
    public void createWeatherController_nullPort_returnsBadRequest() throws Exception {
        WeatherHardwareController hardwareController = this.weatherTestService.createWeatherHardwareController();
        WeatherSensor temperatureSensor = this.weatherTestService.createWeatherSensor();
        hardwareController.getSensors().add(temperatureSensor);

        String controllerResponseString = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        InvalidRequestError result = this.objectMapper.readValue(controllerResponseString, InvalidRequestError.class);
        assertNotNull(result);
        assertEquals("sensors[0].port", result.getFields().get(0).getField());
        assertEquals("Port cannot be null", result.getFields().get(0).getReason());
        assertEquals("Invalid request", result.getMessage());
    }

    /**
     * Given a controller of type weather with an invalid sensorType
     * When a POST request is made to /hardwarecontroller/
     * Then the response status is 400
     * And the response body contains an error message
     *
     */
    @Test
    public void createWeatherController_invalidSensorType_returnsBadRequest() throws Exception {
        WeatherHardwareController hardwareController = this.weatherTestService.createWeatherHardwareController();
        WeatherSensor temperatureSensor = this.weatherTestService.createWeatherSensor();
        temperatureSensor.setPort("INVALID_SENSOR_TYPE");
        hardwareController.getSensors().add(temperatureSensor);

        String controllerResponseString = this.mockMvc.perform(post("/hardwarecontroller/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareController)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        InvalidRequestError result = this.objectMapper.readValue(controllerResponseString, InvalidRequestError.class);
        assertNotNull(result);
        assertEquals("sensors[0].port", result.getFields().get(0).getField());
        assertEquals("Invalid weather sensor type", result.getFields().get(0).getReason());
        assertEquals("Invalid request", result.getMessage());
    }
}
