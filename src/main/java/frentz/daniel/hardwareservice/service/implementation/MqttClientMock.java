package frentz.daniel.hardwareservice.service.implementation;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttClientMock implements IMqttClient {

    private static Logger logger = LoggerFactory.getLogger(MqttClientMock.class);
    @Override
    public void connect() throws MqttSecurityException, MqttException {

    }

    @Override
    public void connect(MqttConnectOptions mqttConnectOptions) throws MqttSecurityException, MqttException {

    }

    @Override
    public IMqttToken connectWithResult(MqttConnectOptions mqttConnectOptions) throws MqttSecurityException, MqttException {
        return null;
    }

    @Override
    public void disconnect() throws MqttException {

    }

    @Override
    public void disconnect(long l) throws MqttException {

    }

    @Override
    public void disconnectForcibly() throws MqttException {

    }

    @Override
    public void disconnectForcibly(long l) throws MqttException {

    }

    @Override
    public void disconnectForcibly(long l, long l1) throws MqttException {

    }

    @Override
    public void subscribe(String s) throws MqttException, MqttSecurityException {

    }

    @Override
    public void subscribe(String[] strings) throws MqttException {

    }

    @Override
    public void subscribe(String s, int i) throws MqttException {

    }

    @Override
    public void subscribe(String[] strings, int[] ints) throws MqttException {

    }

    @Override
    public void subscribe(String s, IMqttMessageListener iMqttMessageListener) throws MqttException, MqttSecurityException {

    }

    @Override
    public void subscribe(String[] strings, IMqttMessageListener[] iMqttMessageListeners) throws MqttException {

    }

    @Override
    public void subscribe(String s, int i, IMqttMessageListener iMqttMessageListener) throws MqttException {

    }

    @Override
    public void subscribe(String[] strings, int[] ints, IMqttMessageListener[] iMqttMessageListeners) throws MqttException {

    }

    @Override
    public IMqttToken subscribeWithResponse(String s) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String s, IMqttMessageListener iMqttMessageListener) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String s, int i) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String s, int i, IMqttMessageListener iMqttMessageListener) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String[] strings) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String[] strings, IMqttMessageListener[] iMqttMessageListeners) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String[] strings, int[] ints) throws MqttException {
        return null;
    }

    @Override
    public IMqttToken subscribeWithResponse(String[] strings, int[] ints, IMqttMessageListener[] iMqttMessageListeners) throws MqttException {
        return null;
    }

    @Override
    public void unsubscribe(String s) throws MqttException {

    }

    @Override
    public void unsubscribe(String[] strings) throws MqttException {

    }

    @Override
    public void publish(String s, byte[] bytes, int i, boolean b) throws MqttException, MqttPersistenceException {

    }

    @Override
    public void publish(String queue, MqttMessage mqttMessage) throws MqttException, MqttPersistenceException {
        this.logger.debug("Queue " + queue);
        this.logger.debug("Message " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void setCallback(MqttCallback mqttCallback) {

    }

    @Override
    public MqttTopic getTopic(String s) {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public String getClientId() {
        return null;
    }

    @Override
    public String getServerURI() {
        return null;
    }

    @Override
    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return new IMqttDeliveryToken[0];
    }

    @Override
    public void setManualAcks(boolean b) {

    }

    @Override
    public void reconnect() throws MqttException {

    }

    @Override
    public void messageArrivedComplete(int i, int i1) throws MqttException {

    }

    @Override
    public void close() throws MqttException {

    }
}
