package urbanjungletech.hardwareservice.helpers.services.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardware;
import urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller.MockHardwareController;
import urbanjungletech.hardwareservice.helpers.mock.sensor.MockSensor;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardware.MqttHardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;
import urbanjungletech.hardwareservice.model.sensor.MqttSensor;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class HardwareControllerTestService {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    public HardwareControllerTestService(ObjectMapper objectMapper,
                                         MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    public HardwareController postHardwareController(HardwareController hardwareController) throws Exception{
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult result = mockMvc.perform(post("/hardwarecontroller/")
                        .content(hardwareControllerJson)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        HardwareController hardwareControllerResponse = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);
        return hardwareControllerResponse;
    }

    public HardwareController putHardwareController(HardwareController hardwareController) throws Exception {
        String hardwareControllerJson = objectMapper.writeValueAsString(hardwareController);
        MvcResult hardwareControllerResponse = mockMvc.perform(put("/hardwarecontroller/" + hardwareController.getId())
                        .content(hardwareControllerJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        HardwareController result = objectMapper.readValue(hardwareControllerResponse.getResponse().getContentAsString(), HardwareController.class);
        return result;
    }

    public void deleteHardwareController(long hardwareControllerId) throws Exception{
        mockMvc.perform(delete("/hardwarecontroller/" + hardwareControllerId))
                .andReturn();
    }

    public Hardware createDefaultHardware(String name){
        MockHardware hardware = new MockHardware();
        hardware.setName(name);
        hardware.setPossibleStates(List.of("on", "off"));
        hardware.setOffState("off");
        return hardware;
    }



    public HardwareController createMqttHardwareControllerWithSensors(List<Sensor> sensors) throws Exception{
        HardwareController hardwareController = createMqttHardwareController();
        hardwareController.setSensors(sensors);
        HardwareController result = this.postHardwareController(hardwareController);
        return result;
    }

    public HardwareController createMqttHardwareController(List<Hardware> hardware) throws Exception{
        HardwareController hardwareController = createMqttHardwareController();
        hardwareController.setHardware(hardware);
        HardwareController result = this.postHardwareController(hardwareController);
        return result;
    }

    public MqttHardwareController createMqttHardwareController() {
        MqttHardwareController hardwareController = new MqttHardwareController();
        HardwareMqttClient hardwareMqttClient = new HardwareMqttClient();
        hardwareController.setName("test");
        hardwareMqttClient.setBrokerUrl("tcp://localhost:1883");
        hardwareMqttClient.setRequestTopic("1234ToMicro");
        hardwareMqttClient.setResponseTopic("HardwareServer");
        hardwareController.setHardwareMqttClient(hardwareMqttClient);
        hardwareController.setSerialNumber("1234");
        return hardwareController;
    }

    public HardwareController createMockHardwareController() {
        HardwareController result = new MockHardwareController();
        result.setSerialNumber("1234");
        result.setName("test");
        return result;
    }

    public HardwareController createMockHardwareControllerWithHardware(List<Hardware> hardware) {
        HardwareController result = createMockHardwareController();
        result.setHardware(hardware);
        return result;
    }

    public MqttHardware createMqttHardware() {
        MqttHardware hardware = new MqttHardware();
        hardware.setName("test");
        hardware.setPort("1");
        hardware.setPossibleStates(List.of("on", "off"));
        hardware.setOffState("off");
        return hardware;
    }

    public HardwareController createMockHardwareControllerWithSensors(List<Sensor> sensors) {
        HardwareController result = createMockHardwareController();
        result.setSensors(sensors);
        return result;
    }

    public HardwareController createMockHardwareControllerWithDefaultHardware() {
        Hardware hardware = createDefaultHardware("hardware1");
        HardwareController result = createMockHardwareControllerWithHardware(List.of(hardware));
        return result;
    }



    public Sensor createSensor(String name) {
        Sensor sensor = new MockSensor();
        sensor.setName(name);
        sensor.setPort("1");
        return sensor;
    }

    public List<HardwareController> findAllHardwareControllers() throws Exception {
        String response = this.mockMvc.perform(get("/hardwarecontroller/"))
                .andReturn().getResponse().getContentAsString();
        return this.objectMapper.readValue(response, new TypeReference<List<HardwareController>>(){

        });
    }

    public List<Sensor> findAllSensors() throws Exception {
        String response = this.mockMvc.perform(get("/sensor/"))
                .andReturn().getResponse().getContentAsString();
        return this.objectMapper.readValue(response, new TypeReference<List<Sensor>>(){

        });
    }

    public HardwareController findHardwareControllerById(Long id) throws Exception {
        String response = this.mockMvc.perform(get("/hardwarecontroller/" + id))
                .andReturn().getResponse().getContentAsString();
        if(response.isEmpty()){
            return null;
        }
        return this.objectMapper.readValue(response, HardwareController.class);
    }

    public Sensor findSensor(long sensorId) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/sensor/" + sensorId))
                        .andReturn();
        if(result.getResponse().getStatus() == 404){
            return null;
        }
        String sensorResponse = result.getResponse().getContentAsString();
        return this.objectMapper.readValue(sensorResponse, Sensor.class);
    }

    public Sensor createMqttSensor() {
        MqttSensor sensor = new MqttSensor();
        sensor.setName("test");
        sensor.setPort("1");
        return sensor;
    }
}
