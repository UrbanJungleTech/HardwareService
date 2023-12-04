package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;

@RestController
@RequestMapping("/credentials")
public class CredentialEndpoint {

    private final CredentialsAdditionService credentialsAdditionService;

    public CredentialEndpoint(CredentialsAdditionService credentialsAdditionService){
        this.credentialsAdditionService = credentialsAdditionService;
    }
    @PostMapping("/")
    public ResponseEntity<Credentials> createCredentials(@RequestBody Credentials credentials){
        Credentials result = this.credentialsAdditionService.create(credentials);
        return ResponseEntity.ok(result);
    }
}
