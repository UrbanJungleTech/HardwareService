package frentz.daniel.hardwareservice.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.exception.MqttPublishException;
import frentz.daniel.hardwareservice.jsonrpc.RpcResponseProcessor;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import frentz.daniel.hardwareservice.service.MqttService;
import io.reactivex.Observable;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MqttServiceImpl implements MqttService {

    Logger logger = LoggerFactory.getLogger(MqttServiceImpl.class);
    private IMqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    private ObjectMapper objectMapper;
    private AtomicLong sequenceGenerator;
    private RpcResponseProcessor rpcResponseProcessor;

    public MqttServiceImpl(IMqttClient mqttClient,
                           MqttConnectOptions mqttConnectOptions,
                           ObjectMapper objectMapper,
                           AtomicLong sequenceGenerator,
                           RpcResponseProcessor rpcResponseProcessor){
        this.mqttClient = mqttClient;
        this.mqttConnectOptions = mqttConnectOptions;
        this.objectMapper = objectMapper;
        this.sequenceGenerator = sequenceGenerator;
        this.rpcResponseProcessor = rpcResponseProcessor;
    }

    @Override
    public void publish(String serialNumber, JsonRpcMessage rpcMessage) {
        try {
            String payload = this.objectMapper.writeValueAsString(rpcMessage);
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(2);
            message.setRetained(true);
            while(!this.mqttClient.isConnected()){
                Thread.sleep(100);
            }
            this.mqttClient.publish(serialNumber + "ToMicrocontroller", message);
            logger.debug("Sent RPC message -> {} ", message);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new MqttPublishException(ex);
        }
    }

    @Override
    public Observable<JsonRpcMessage> publishWithResponse(String serialNumber, JsonRpcMessage payload, long timeout) {
        logger.debug("Publishing with response: {}", payload);
        long id = this.sequenceGenerator.getAndIncrement() + 1;
        payload.setId(id);
        Observable<JsonRpcMessage> result = this.rpcResponseProcessor.awaitResponse(id, timeout);
        this.publish(serialNumber, payload);
        return result;
    }
}