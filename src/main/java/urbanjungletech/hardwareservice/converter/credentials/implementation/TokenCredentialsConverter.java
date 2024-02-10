package urbanjungletech.hardwareservice.converter.credentials.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.entity.credentials.TokenCredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;

@Service
public class TokenCredentialsConverter implements SpecificCredentialsConverter<TokenCredentials, TokenCredentialsEntity> {
    @Override
    public TokenCredentials toModel(TokenCredentialsEntity entity) {
        TokenCredentials result = new TokenCredentials();
        result.setTokenValue(entity.getTokenValue());
        result.setId(entity.getId());
        return result;
    }

    @Override
    public TokenCredentialsEntity createEntity(TokenCredentials tokenCredentials) {
        TokenCredentialsEntity result = new TokenCredentialsEntity();
        this.fillEntity(result, tokenCredentials);
        return result;
    }

    @Override
    public void fillEntity(TokenCredentialsEntity entity, TokenCredentials tokenCredentials) {
        entity.setTokenValue(tokenCredentials.getTokenValue());
    }
}
