package urbanjungletech.hardwareservice.helpers.services.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Service
public class ScheduledSensorReadingTestService {

    private SensorTestService sensorTestService;
    private MockMvc mockmvc;
    private ObjectMapper objectMapper;
    private final HardwareTestService hardwareTestService;
    private final HardwareControllerTestService hardwareControllerTestService;
    public ScheduledSensorReadingTestService(SensorTestService sensorTestService,
                                             MockMvc mockmvc,
                                             ObjectMapper objectMapper,
                                             HardwareTestService hardwareTestService,
                                             HardwareControllerTestService hardwareControllerTestService){
        this.sensorTestService = sensorTestService;
        this.mockmvc = mockmvc;
        this.objectMapper = objectMapper;
        this.hardwareTestService = hardwareTestService;
        this.hardwareControllerTestService = hardwareControllerTestService;
    }

    public ScheduledSensorReading generateBasicScheduledSensorReading(){
        ScheduledSensorReading result = new ScheduledSensorReading();
        result.setCronString("0 0 0 1 1 ? 2099");
        return result;
    }
    public ScheduledSensorReading createBasicScheduledReading(Alert...alerts) throws Exception {
        HardwareController hardwareController = this.sensorTestService.createBasicMockSensor();
        hardwareController = this.hardwareControllerTestService.postHardwareController(hardwareController);
        Sensor sensor = hardwareController.getSensors().get(0);
        ScheduledSensorReading scheduledSensorReading = this.generateBasicScheduledSensorReading();
        sensor.getScheduledSensorReadings().add(scheduledSensorReading);
        String sensorString = this.mockmvc.perform(put("/sensor/" + sensor.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(sensor)))
                .andReturn().getResponse().getContentAsString();
        sensor = this.objectMapper.readValue(sensorString, Sensor.class);
        return sensor.getScheduledSensorReadings().get(0);
    }
}
