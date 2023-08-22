package urbanjungletech.hardwareservice.config;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingMqttMessageInterceptor implements InterceptHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingMqttMessageInterceptor.class);

    @Override
    public String getID() {
        return LoggingMqttMessageInterceptor.class.getName();
    }

    @Override
    public Class<?>[] getInterceptedMessageTypes() {
        return new Class[]{InterceptConnectMessage.class, InterceptDisconnectMessage.class, InterceptConnectionLostMessage.class,
                InterceptPublishMessage.class, InterceptSubscribeMessage.class, InterceptUnsubscribeMessage.class, InterceptAcknowledgedMessage.class};
    }

    @Override
    public void onConnect(InterceptConnectMessage interceptConnectMessage) {

    }

    @Override
    public void onDisconnect(InterceptDisconnectMessage interceptDisconnectMessage) {

    }

    @Override
    public void onConnectionLost(InterceptConnectionLostMessage interceptConnectionLostMessage) {

    }

    @Override
    public void onPublish(InterceptPublishMessage interceptPublishMessage) {
        LOGGER.info("Message was published. Topic: {}. Payload: {}", interceptPublishMessage.getTopicName(), interceptPublishMessage.getPayload().toString());
    }

    @Override
    public void onSubscribe(InterceptSubscribeMessage interceptSubscribeMessage) {

    }

    @Override
    public void onUnsubscribe(InterceptUnsubscribeMessage interceptUnsubscribeMessage) {

    }

    @Override
    public void onMessageAcknowledged(InterceptAcknowledgedMessage interceptAcknowledgedMessage) {
        LOGGER.info("Message arrived. Topic: {}. Payload: {}", interceptAcknowledgedMessage.getTopic(), interceptAcknowledgedMessage.getMsg());
    }
}
