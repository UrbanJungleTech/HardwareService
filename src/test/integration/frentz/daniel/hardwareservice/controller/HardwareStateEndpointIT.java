package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.HardwareTestService;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.ONOFF;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HardwareStateEndpointIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HardwareTestService hardwareTestService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HardwareControllerRepository hardwareControllerRepository;

    @BeforeEach
    public void setup(){
        hardwareControllerRepository.deleteAll();
        hardwareControllerRepository.flush();
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a GET request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 200 OK
     * And the response body should be the desiredState of the hardware
     */
    @Test
    public void getHardwareStateById() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        HardwareState desiredState = createdHardwareController.getHardware().get(0).getDesiredState();

        this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(desiredState.getId()))
                .andExpect(jsonPath("$.hardwareId").value(desiredState.getHardwareId()))
                .andExpect(jsonPath("$.state").value(desiredState.getState().toString()));
    }

    /**
     * Given an id which is not associatd with a hardware state
     * When a GET request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 404 NOT FOUND
     */
    @Test
    public void getHardwareStateByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/hardwarestate/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given a HardwareController has been created via the endpoint /hardwarecontroller with a single hardware
     * When a PUT request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 200 OK
     * And the response body should be the updated desiredState of the hardware
     * And a GET call to /hardwarestate should return the updated desiredState
     */
    @Test
    public void updateHardwareState() throws Exception {
        HardwareController createdHardwareController = this.hardwareTestService.createBasicHardware();
        HardwareState desiredState = createdHardwareController.getHardware().get(0).getDesiredState();
        desiredState.setState(ONOFF.ON);
        desiredState.setLevel(50);

        this.mockMvc.perform(put("/hardwarestate/" + desiredState.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(desiredState)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(desiredState.getId()))
                .andExpect(jsonPath("$.hardwareId").value(desiredState.getHardwareId()))
                .andExpect(jsonPath("$.state").value(desiredState.getState().toString()))
                .andExpect(jsonPath("$.level").value(desiredState.getLevel()));

        this.mockMvc.perform(get("/hardwarestate/" + desiredState.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(desiredState.getId()))
                .andExpect(jsonPath("$.hardwareId").value(desiredState.getHardwareId()))
                .andExpect(jsonPath("$.state").value(desiredState.getState().toString()))
                .andExpect(jsonPath("$.level").value(desiredState.getLevel()));
    }

    /**
     * Given an id which is not associatd with a hardware state
     * When a PUT request is made to /hardwarestate/{hardwareStateId} using the ID of the hardwares desiredState
     * Then the response should be 404 NOT FOUND
     */
    @Test
    public void updateHardwareStateNotFound() throws Exception {
        HardwareState desiredState = new HardwareState();

        this.mockMvc.perform(put("/hardwarestate/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(desiredState)))
                .andExpect(status().isNotFound());
    }

}
