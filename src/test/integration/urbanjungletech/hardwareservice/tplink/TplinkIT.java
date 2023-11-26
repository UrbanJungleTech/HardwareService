package urbanjungletech.hardwareservice.tplink;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
public class TplinkIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;

    @Autowired
    ObjectMapper objectMapper;
    /**
     * Given a hardware controller of type tplink has been created with a single hardware with the configuration options:
     * macAddress: 34-60-F9-F0-5D-E4
     * type: plug
     * Then when the state is updated via /hardwarestate/{hardwareStateId} with the state ON
     * Then the state of the hardware should be ON
     * TODO: need to reponder this as it only works locally.
     */
//    @Test
//    public void test() throws Exception {
//        String macAddress = "34-60-F9-F0-5D-E4";
//        String type = "plug";
//        Hardware hardware = new Hardware();
//        Map<String, String> configuration = new HashMap<>();
//        configuration.put("macAddress", macAddress);
//        configuration.put("type", type);
//        hardware.setConfiguration(configuration);
//        HardwareController hardwareController = hardwareControllerTestService.addBasicHardwareControllerWithHardware(List.of(hardware));
//        hardware = hardwareController.getHardware().get(0);
//
//        HardwareState desiredState = hardware.getDesiredState();
//        desiredState.setState(ONOFF.ON);
//
//        String payload = objectMapper.writeValueAsString(desiredState);
//        this.mockMvc.perform(put("/hardwarestate/" + desiredState.getId())
//                .contentType("application/json")
//                .content(payload))
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(get("/hardwarestate/" + hardware.getDesiredState().getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(hardware.getDesiredState().getId()))
//                .andExpect(jsonPath("$.hardwareId").value(hardware.getDesiredState().getHardwareId()))
//                .andExpect(jsonPath("$.state").value(hardware.getDesiredState().getState().toString()));
//    }
}
