package urbanjungletech.hardwareservice.endpoint;

import com.azure.security.keyvault.secrets.SecretClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CredentialsEndingIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecretClient secretClient;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Given a UsernamePasswordCredentials object
     * When the object is sent to the /credentials endpoint via a POST request
     * Then the object is saved in the database
     * And the response status is 200
     * And the response body is the credentials object with the keys stored in azure key vault
     */
    @Test
    public void testUsernamePasswordCredentials() throws Exception {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
        credentials.setUsername("username");
        credentials.setPassword("password");
        String responseString = this.mockMvc.perform(post("/credentials/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        UsernamePasswordCredentials responseCredentials = objectMapper.readValue(responseString, UsernamePasswordCredentials.class);
        String usernameStoredInKeyVault = secretClient.getSecret(responseCredentials.getUsername()).getValue();
        assertEquals(credentials.getUsername(), usernameStoredInKeyVault);
        String passwordStoredInKeyVault = secretClient.getSecret(responseCredentials.getPassword()).getValue();
        assertEquals(credentials.getPassword(), passwordStoredInKeyVault);
        assertEquals(credentials.getUsername(), usernameStoredInKeyVault);
        assertEquals(credentials.getPassword(), passwordStoredInKeyVault);
    }

    /**
     * Given a TokenCredentials object
     * When the object is sent to the /credentials endpoint via a POST request
     * Then the object is saved in the database
     * And the response status is 200
     * And the response body is the credentials object with the keys stored in azure key vault
     */
    @Test
    public void testTokenCredentials() throws Exception {
        TokenCredentials credentials = new TokenCredentials();
        credentials.setTokenValue("token1");
        credentials.setUrl("url1");
        String responseString = this.mockMvc.perform(post("/credentials/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TokenCredentials responseCredentials = objectMapper.readValue(responseString, TokenCredentials.class);
        String tokenStoredInKeyVault = secretClient.getSecret(responseCredentials.getTokenValue()).getValue();
        assertEquals(credentials.getTokenValue(), tokenStoredInKeyVault);
        String urlStoredInKeyVault = secretClient.getSecret(responseCredentials.getUrl()).getValue();
        assertEquals(credentials.getUrl(), urlStoredInKeyVault);
        assertEquals(credentials.getTokenValue(), tokenStoredInKeyVault);
        assertEquals(credentials.getUrl(), urlStoredInKeyVault);

    }


}
