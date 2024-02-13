package urbanjungletech.hardwareservice.config.digitaltwin;

import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredential;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DigitalTwinsClientConfiguration {


    @Bean
    @ConditionalOnProperty(name = "digitaltwins.enabled", havingValue = "true")
    public DigitalTwinsClient digitalTwinClient(DefaultAzureCredential credential,
                                                DigitalTwinsConfiguration digitalTwinsConfiguration) {
        return new DigitalTwinsClientBuilder()
                .credential(credential)
                .endpoint(digitalTwinsConfiguration.getDigitalTwinsUri())
                .buildClient();
    }
}
