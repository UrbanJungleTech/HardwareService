package frentz.daniel.controller.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private HardwareRabbitConfig hardwareRabbitConfig;
    private SensorRabbitConfig sensorRabbitConfig;

    public RabbitMQConfig(HardwareRabbitConfig hardwareRabbitConfig,
                          SensorRabbitConfig sensorRabbitConfig){
        this.hardwareRabbitConfig = hardwareRabbitConfig;
        this.sensorRabbitConfig = sensorRabbitConfig;
    }



    @Bean
    SimpleMessageListenerContainer stateConfirmationListenerContainer(ConnectionFactory connectionFactory,
                                             StateConfirmationReceiver stateConfirmationReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(this.hardwareRabbitConfig.getStateConfirmation().getQueue());
        container.setMessageListener(stateConfirmationReceiver);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer hardwareRegistrationListenerContainer(ConnectionFactory connectionFactory,
                                             HardwareRegistrationReceiver hardwareRegistrationReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(this.hardwareRabbitConfig.getRegistration().getQueue());
        container.setMessageListener(hardwareRegistrationReceiver);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer sensorRegistrationListenerContainer(ConnectionFactory connectionFactory,
                                                                         SensorRegistrationReceiver sensorRegistrationReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(this.sensorRabbitConfig.getRegistration().getQueue());
        container.setMessageListener(sensorRegistrationReceiver);
        return container;
    }

    @Bean
    Queue hardwareQueue(){
        return new Queue(this.hardwareRabbitConfig.getRegistration().getQueue(), false);
    }

    @Bean
    Queue sensorQueue(){
        return new Queue(this.sensorRabbitConfig.getRegistration().getQueue(), false);
    }

    @Bean
    Queue stateConfirmationQueue() {
        return new Queue(this.hardwareRabbitConfig.getStateConfirmation().getQueue() , false);
    }

}
