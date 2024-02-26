package urbanjungletech.hardwareservice.helpers.services.http;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensor;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SensorTestService {

    private HardwareControllerTestService hardwareControllerTestService;
    private AtomicInteger portCounter;
    public SensorTestService(HardwareControllerTestService hardwareControllerTestService) {
        this.hardwareControllerTestService = hardwareControllerTestService;
        this.portCounter = new AtomicInteger(1);
    }

    /**
     * Creates a hardware controller with a sensor with the fields:
     * port: 1
     * name: "sensor1"
     * sensorType: "temperature"
     *
     * @return the created hardwarecontroller which contains the sensor
     */
    public HardwareController createMqttSensor() throws Exception{
        Sensor sensor = new MockSensor();
        sensor.setPort(String.valueOf(this.portCounter.getAndIncrement()));
        sensor.setName(UUID.randomUUID().toString());
        HardwareController result = this.hardwareControllerTestService.createMockHardwareControllerWithSensors(List.of(sensor));
        return result;
    }

    public HardwareController createBasicMockSensor() throws Exception{
        Sensor sensor = new MockSensor();
        sensor.setPort(String.valueOf(this.portCounter.getAndIncrement()));
        sensor.setName(UUID.randomUUID().toString());
        HardwareController result = this.hardwareControllerTestService.createMockHardwareController();
        result.getSensors().add(sensor);
        return result;
    }


}
