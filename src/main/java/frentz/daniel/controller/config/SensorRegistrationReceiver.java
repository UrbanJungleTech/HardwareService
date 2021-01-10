package frentz.daniel.controller.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public interface SensorRegistrationReceiver extends MessageListener {
    void onMessage(Message message);
}
