package urbanjungletech.hardwareservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
        @Bean
        @ConditionalOnProperty(name = "spring.security.type", havingValue = "oauth2")
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(oauth2ResourceServer ->
                            oauth2ResourceServer
                                    .jwt(withDefaults())
                    )
                    .sessionManagement(sessionManagement ->
                            sessionManagement
                                    .disable()
                    );
            return http.build();
        }

        @Bean
        @ConditionalOnProperty(name = "spring.security.type", havingValue = "none")
        public SecurityFilterChain noSecurity(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .anyRequest().permitAll()
                    )
                    .csrf((csrfConfigurer) -> {
                        csrfConfigurer.disable();
                    })
                    .sessionManagement(sessionManagement ->
                            sessionManagement
                                    .disable()
                    );
            return http.build();
        }
}
