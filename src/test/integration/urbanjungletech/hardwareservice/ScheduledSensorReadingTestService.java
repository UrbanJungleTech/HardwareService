package urbanjungletech.hardwareservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Service
public class ScheduledSensorReadingTestService {

    private SensorTestService sensorTestService;
    private MockMvc mockmvc;
    private ObjectMapper objectMapper;
    public ScheduledSensorReadingTestService(SensorTestService sensorTestService,
                                             MockMvc mockmvc,
                                             ObjectMapper objectMapper){
        this.sensorTestService = sensorTestService;
        this.mockmvc = mockmvc;
        this.objectMapper = objectMapper;
    }

    public ScheduledSensorReading generateBasicScheduledSensorReading(){
        ScheduledSensorReading result = new ScheduledSensorReading();
        result.setCronString("0 0 0 1 1 ? 2099");
        return result;
    }
    public ScheduledSensorReading createBasicScheduledReading(SensorReadingAlert...alerts) throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicSensor();
        Sensor sensor = hardwareController.getSensors().get(0);
        ScheduledSensorReading scheduledSensorReading = this.generateBasicScheduledSensorReading();
        scheduledSensorReading.setSensorReadingAlerts(List.of(alerts));
        sensor.getScheduledSensorReadings().add(scheduledSensorReading);
        String sensorString = this.mockmvc.perform(put("/sensor/" + sensor.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(sensor)))
                .andReturn().getResponse().getContentAsString();
        sensor = this.objectMapper.readValue(sensorString, Sensor.class);
        return sensor.getScheduledSensorReadings().get(0);
    }
}
