package frentz.daniel.hardwareservice.service.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.jsonrpc.RpcResponseProcessor;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import io.reactivex.Observable;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MqttServiceImpl implements MqttService {

    private final Logger logger = LoggerFactory.getLogger(MqttServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final AtomicLong sequenceGenerator;
    private final RpcResponseProcessor rpcResponseProcessor;
    private final Map<String, List<MqttMessage>> failedMessages;
    private HardwareControllerService hardwareControllerService;
    private MqttClient mqttClient;

    public MqttServiceImpl(ObjectMapper objectMapper,
                           AtomicLong sequenceGenerator,
                           RpcResponseProcessor rpcResponseProcessor,
                           HardwareControllerService hardwareControllerService,
                           MqttClient mqttClient){
        this.objectMapper = objectMapper;
        this.sequenceGenerator = sequenceGenerator;
        this.rpcResponseProcessor = rpcResponseProcessor;
        this.failedMessages = new HashMap<>();
        this.hardwareControllerService = hardwareControllerService;
        this.mqttClient = mqttClient;
    }

    @Override
    public void publish(String serialNumber, JsonRpcMessage rpcMessage) {
        MqttMessage message = null;
        try {
            String payload = this.objectMapper.writeValueAsString(rpcMessage);
            message = new MqttMessage(payload.getBytes());
            message.setQos(2);
            message.setRetained(true);
            this.mqttClient.publish(serialNumber, message);
            logger.debug("Sent RPC message -> {} ", message);
        }
        catch(Exception ex){
            Optional.of(message).ifPresent((msg) -> {
                if(!failedMessages.containsKey(serialNumber)){
                    List<MqttMessage> messages = new ArrayList<>();
                    failedMessages.put(serialNumber, messages);
                }
                else{
                    failedMessages.get(serialNumber).add(msg);
                }
            });
            ex.printStackTrace();
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

    @Scheduled(fixedDelay = 100)
    public void retryFailedMessages(){
        Set<String> serialNumbers = this.failedMessages.keySet();
        serialNumbers.forEach(serialNumber -> {
            List<MqttMessage> messages = this.failedMessages.get(serialNumber);
            messages.forEach(message -> {
                try {
                    this.failedMessages.remove(message);
                    this.mqttClient.publish(serialNumber, message);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            });
        });
    }
}
