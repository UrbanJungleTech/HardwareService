package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import urbanjungletech.hardwareservice.services.http.HardwareControllerTestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HardwareControllerGroupEndpointIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    HardwareControllerTestService hardwareControllerTestService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Given a HardwareControllerGroup
     * When a POST request is made to /hardwareControllerGroup
     * Then the HardwareControllerGroup is created
     * And the HardwareControllerGroup is returned with an id
     */
    @Test
    public void createHardwareControllerGroup() throws Exception {
        HardwareControllerGroup hardwareControllerGroup = new HardwareControllerGroup();
        hardwareControllerGroup.setName("test");

        String responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertNotNull(response.getId());
    }

    /**
     * Given a HardwareController is created
     * And Given a HardwareControllerGroup which has the hardwareController in its hardwareControllers list
     * When a POST request is made to /hardwareControllerGroup
     * Then the HardwareControllerGroup is created
     * And the HardwareControllerGroup has the HardwareController in its hardwareControllers list
     */
    @Test
    public void createHardwareControllerGroupWithHardwareController() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createBasicHardwareControllerHttp();
        HardwareControllerGroup hardwareControllerGroup = new HardwareControllerGroup();
        hardwareControllerGroup.setName("test");
        hardwareControllerGroup.setHardwareControllers(List.of(hardwareController.getId()));

        String responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertNotNull(response.getId());
        assertEquals(1, response.getHardwareControllers().size());
        assertEquals(hardwareController.getId(), response.getHardwareControllers().get(0));
    }

    /**
     * Given a HardwareController is created
     * And Given a HardwareControllerGroup which has the hardwareController in its hardwareControllers list is created
     * When a GET request is made to /hardwareControllerGroup/{id}
     * Then the HardwareControllerGroup is returned
     */
    @Test
    public void getHardwareControllerGroupWithHardwareController() throws Exception {
        HardwareController hardwareController = this.hardwareControllerTestService.createBasicHardwareControllerHttp();
        HardwareControllerGroup hardwareControllerGroup = new HardwareControllerGroup();
        hardwareControllerGroup.setName("test");
        hardwareControllerGroup.setHardwareControllers(List.of(hardwareController.getId()));

        String responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertNotNull(response.getId());
        assertEquals(1, response.getHardwareControllers().size());
        assertEquals(hardwareController.getId(), response.getHardwareControllers().get(0));

        responseString = this.mockMvc.perform(get("/hardwarecontrollergroup/" + response.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        response = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertNotNull(response.getId());
        assertEquals(1, response.getHardwareControllers().size());
        assertEquals(hardwareController.getId(), response.getHardwareControllers().get(0));
    }

    /**
     * Given 2 HardwareControllerGroups are created
     * When a GET request is made to /hardwareControllerGroup/
     * Then the HardwareControllerGroups are returned
     */
    @Test
    public void getHardwareControllerGroups() throws Exception {
        HardwareControllerGroup hardwareControllerGroup1 = new HardwareControllerGroup();
        hardwareControllerGroup1.setName("test1");

        HardwareControllerGroup hardwareControllerGroup2 = new HardwareControllerGroup();
        hardwareControllerGroup2.setName("test2");

        String responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup1)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response1 = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertNotNull(response1.getId());

        responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup2)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response2 = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertNotNull(response2.getId());

        responseString = this.mockMvc.perform(get("/hardwarecontrollergroup/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<HardwareControllerGroup> response = objectMapper.readValue(responseString, List.class);
        assertEquals(2, response.size());
    }

    /**
     * Given an id which is not associated with a HardwareControllerGroup
     * When a DELETE request is made to /hardwareControllerGroup/{id}
     * Then a 404 is returned
     */
    @Test
    public void deleteHardwareControllerGroupNotFound() throws Exception {
        this.mockMvc.perform(delete("/hardwarecontrollergroup/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is not associated with a HardwareControllerGroup
     * When a GET request is made to /hardwareControllerGroup/{id}
     * Then a 404 is returned
     */
    @Test
    public void getHardwareControllerGroupNotFound() throws Exception {
        this.mockMvc.perform(delete("/hardwarecontrollergroup/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is associated with a HardwareControllerGroup
     * When a DELETE request is made to /hardwareControllerGroup/{id}
     * Then the HardwareControllerGroup is deleted
     * And a no content response is returned
     * And the HardwareControllerGroup is no longer returned by GET requests to /hardwareControllerGroup/{id}
     */
    @Test
    public void deleteHardwareControllerGroup() throws Exception {
        HardwareControllerGroup hardwareControllerGroup = new HardwareControllerGroup();
        hardwareControllerGroup.setName("test");

        String responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response = objectMapper.readValue(responseString, HardwareControllerGroup.class);

        this.mockMvc.perform(delete("/hardwarecontrollergroup/" + response.getId()))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/hardwarecontrollergroup/" + response.getId()))
                .andExpect(status().isNotFound());
    }

    /**
     * Given an id which is associated with a HardwareControllerGroup
     * When a GET request is made to /hardwareControllerGroup/{id}
     * Then the HardwareControllerGroup is returned
     */
    @Test
    public void getHardwareControllerGroup() throws Exception {
        HardwareControllerGroup hardwareControllerGroup = new HardwareControllerGroup();
        hardwareControllerGroup.setName("test");

        String responseString = this.mockMvc.perform(post("/hardwarecontrollergroup/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hardwareControllerGroup)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup response = objectMapper.readValue(responseString, HardwareControllerGroup.class);

        responseString = this.mockMvc.perform(get("/hardwarecontrollergroup/" + response.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HardwareControllerGroup getResponse = objectMapper.readValue(responseString, HardwareControllerGroup.class);
        assertEquals(response.getId(), getResponse.getId());
        assertEquals(response.getName(), getResponse.getName());

    }


}
