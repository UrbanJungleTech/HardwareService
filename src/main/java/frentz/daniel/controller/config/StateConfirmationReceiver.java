package frentz.daniel.controller.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public interface StateConfirmationReceiver extends MessageListener {
    void onMessage(Message message);
}
