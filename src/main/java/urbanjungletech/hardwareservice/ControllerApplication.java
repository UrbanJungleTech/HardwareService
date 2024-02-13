package urbanjungletech.hardwareservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.quartz.QuartzEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.atomic.AtomicLong;


@SpringBootApplication(exclude={QuartzEndpointAutoConfiguration.class})
@EnableAspectJAutoProxy()
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "urbanjungletech",
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager")
@EnableScheduling
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "HardwareService API", version = "v1", description = "API for interacting with the hardware service"))
public class ControllerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication();
		application.run(ControllerApplication.class, args);
	}

	@Bean
	public AtomicLong getAtomicLong(){
		return new AtomicLong();
	}
}
