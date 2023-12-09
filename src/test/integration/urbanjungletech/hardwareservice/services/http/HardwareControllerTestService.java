package urbanjungletech.hardwareservice.services.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                        .contentType("application/json")
                        .content(hardwareControllerJson))
                .andExpect(status().isCreated())
                .andReturn();

        HardwareController hardwareControllerResponse = objectMapper.readValue(result.getResponse().getContentAsString(), HardwareController.class);
        return hardwareControllerResponse;
    }

    public HardwareController createHardwareMqttController(){
        HardwareController hardwareController = createMqttHardwareControllerWithHardware();
        hardwareController.setType("mqtt");
        Map<String, String> configuration = hardwareController.getConfiguration();
        configuration.put("serialNumber", "1234");
        configuration.put("server", "tcp://localhost:1883");
        configuration.put("responseQueue", "HardwareServer");
        configuration.put("requestQueue", "1234ToMicro");
        return hardwareController;
    }

    public Hardware createDefaultHardware(String name){
        Hardware hardware = new Hardware();
        hardware.setName(name);
        hardware.setConfiguration(Map.of("testKey", "testValue"));
        hardware.setPossibleStates(List.of("on", "off"));
        hardware.setOffState("off");
        return hardware;
    }



    public HardwareController createMqttHardwareControllerWithSensors(List<Sensor> sensors) throws Exception{
        HardwareController hardwareController = createMqttHardwareControllerWithHardware();
        hardwareController.setSensors(sensors);
        HardwareController result = this.postHardwareController(hardwareController);
        return result;
    }

    public HardwareController createMqttHardwareControllerWithHardware(List<Hardware> hardware) throws Exception{
        HardwareController hardwareController = createMqttHardwareControllerWithHardware();
        hardwareController.setHardware(hardware);
        HardwareController result = this.postHardwareController(hardwareController);
        return result;
    }

    public HardwareController createMqttHardwareControllerWithHardware() {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setType("mqtt");
        hardwareController.getConfiguration().put("serialNumber", "1234");
        hardwareController.getConfiguration().put("server", "tcp://localhost:1883");
        hardwareController.getConfiguration().put("responseQueue", "HardwareServer");
        hardwareController.getConfiguration().put("requestQueue", "1234ToMicro");
        return hardwareController;
    }

    public HardwareController createMockHardwareController() {
        HardwareController result = createMqttHardwareControllerWithHardware();
        result.setType("mock");
        return result;
    }

    public HardwareController createMockHardwareControllerWithHardware(List<Hardware> hardware) {
        HardwareController result = createMockHardwareController();
        result.setHardware(hardware);
        return result;
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
        Sensor sensor = new Sensor();
        sensor.setName(name);
        sensor.setSensorType("temperature");
        sensor.setConfiguration(Map.of("testKey", "testValue"));
        sensor.setPort("1");
        sensor.setMetadata(Map.of("testKey", "testValue"));
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
}
