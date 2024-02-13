package urbanjungletech.hardwareservice.config.router;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseRouterConfiguration {
    @Bean
    public Map<Long, SessionFactory> sessionFactoryCache() {
        return new HashMap<>();
    }
}
