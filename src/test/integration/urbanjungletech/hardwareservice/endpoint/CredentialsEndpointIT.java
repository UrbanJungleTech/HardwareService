package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecureStorageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"development.mqtt.client.enabled=false",
        "development.mqtt.server.enabled=false", "development.mqtt.client.enabled=false",
        "mqtt.server.enabled=false",
        "mqtt-rpc.enabled=false"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EnableAspectJAutoProxy
public class CredentialsEndpointIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecureStorageService secureStorageService;
    //@MockBean
//    @BeforeEach
//    public void before(){
//        Map<String, String> secretCache = new HashMap<>();
//        when(this.mockSecretClient.getSecret(anyString())).thenAnswer(new Answer<Object>() {
//            @Override
//            public KeyVaultSecret answer(InvocationOnMock invocation) throws Throwable {
//                // Get the arguments passed to getSecret
//                Object[] parameters = invocation.getArguments();
//                String secretName = (String) parameters[0];
//
//                // Return a new KeyVaultSecret based on the secret name
//                return new KeyVaultSecret(secretName, secretCache.get(secretName));
//            }
//        });
//
//        when(this.mockSecretClient.setSecret(anyString(), anyString())).thenAnswer(new Answer<Object>() {
//            @Override
//            public KeyVaultSecret answer(InvocationOnMock invocation) throws Throwable {
//                // Get the arguments passed to getSecret
//                Object[] parameters = invocation.getArguments();
//                String secretName = (String) parameters[0];
//                String secretValue = (String) parameters[1];
//                secretCache.put(secretName, secretValue);
//                // Return a new KeyVaultSecret based on the secret name
//                return new KeyVaultSecret(secretName, secretCache.get(secretName));
//            }
//        });
//    }


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
        String usernameStoredInKeyVault = this.secureStorageService.getSecret(responseCredentials.getUsername());
        assertEquals(credentials.getUsername(), usernameStoredInKeyVault);
        String passwordStoredInKeyVault = this.secureStorageService.getSecret(responseCredentials.getPassword());
        assertEquals(credentials.getPassword(), passwordStoredInKeyVault);
        assertEquals(credentials.getUsername(), usernameStoredInKeyVault);
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
        String responseString = this.mockMvc.perform(post("/credentials/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TokenCredentials responseCredentials = objectMapper.readValue(responseString, TokenCredentials.class);
//        String tokenStoredInKeyVault = mockSecretClient.getSecret(responseCredentials.getTokenValue()).getValue();
//        assertEquals(credentials.getTokenValue(), tokenStoredInKeyVault);
//        assertEquals(credentials.getTokenValue(), tokenStoredInKeyVault);

    }


}
