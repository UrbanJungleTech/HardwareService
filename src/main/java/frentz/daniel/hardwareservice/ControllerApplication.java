package frentz.daniel.hardwareservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.quartz.QuartzEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.atomic.AtomicLong;


@SpringBootApplication(exclude={QuartzEndpointAutoConfiguration.class})
@EnableAspectJAutoProxy
@EnableScheduling
@EnableAsync
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
