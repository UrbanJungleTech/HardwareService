package frentz.daniel.controller.config;

import frentz.daniel.service.CronClient;
import frentz.daniel.service.CronClientImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix="cronservice")
public class CronServiceConfig {

    private String uri;

    @Bean
    public CronClient cronClient(RestTemplate restTemplate){
        return new CronClientImpl(URI.create(uri), restTemplate);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
