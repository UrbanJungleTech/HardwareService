package frentz.daniel.hardwareservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Service
@Profile("test")
public class SensorTestService {

    private HardwareControllerTestService hardwareControllerTestService;

    public SensorTestService(HardwareControllerTestService hardwareControllerTestService) {
        this.hardwareControllerTestService = hardwareControllerTestService;
    }

    /**
     * Creates a hardware controller with a sensor with the fields:
     * port: 1
     * name: "sensor1"
     * sensorType: "temperature"
     *
     * @return the created hardwarecontroller which contains the sensor
     */
    public HardwareController createBasicSensor() throws Exception{
        Sensor sensor = new Sensor();
        sensor.setPort(1);
        sensor.setName("sensor1");
        sensor.setSensorType("temperature");
        HardwareController result = this.hardwareControllerTestService.addBasicHardwareControllerWithSensors(List.of(sensor));
        return result;
    }


}
