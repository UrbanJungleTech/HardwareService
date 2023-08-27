package urbanjungletech.hardwareservice.service.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.jsonrpc.RpcResponseProcessor;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;
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
    private final Map<Long, List<JsonRpcMessage>> failedMessages;
    private HardwareControllerQueryService hardwareControllerQueryService;
    private MqttClient mqttClient;

    public MqttServiceImpl(ObjectMapper objectMapper,
                           AtomicLong sequenceGenerator,
                           RpcResponseProcessor rpcResponseProcessor,
                           HardwareControllerQueryService hardwareControllerQueryService,
                           MqttClient mqttClient){
        this.objectMapper = objectMapper;
        this.sequenceGenerator = sequenceGenerator;
        this.rpcResponseProcessor = rpcResponseProcessor;
        this.failedMessages = new HashMap<>();
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.mqttClient = mqttClient;
    }

    @Override
    public void publish(long hardwareControllerId, JsonRpcMessage rpcMessage) {
        MqttMessage message = null;
        String payload = null;
        try {
            payload = this.objectMapper.writeValueAsString(rpcMessage);
            message = new MqttMessage(payload.getBytes());
            message.setQos(2);
            message.setRetained(true);
            this.mqttClient.publish(hardwareControllerId, message);
            logger.debug("Sent RPC message -> {} ", message);
        }
        catch(Exception ex){
                logger.error("Failed to send message: {} for controller {}", payload, hardwareControllerId);
                if(!failedMessages.containsKey(hardwareControllerId)){
                    List<JsonRpcMessage> messages = new ArrayList<>();
                    failedMessages.put(hardwareControllerId, messages);
                }
                    failedMessages.get(hardwareControllerId).add(rpcMessage);
            ex.printStackTrace();
        }
    }

    @Override
    public Observable<JsonRpcMessage> publishWithResponse(long hardwareControllerId, JsonRpcMessage payload, long timeout) {
        logger.debug("Publishing with response: {}", payload);
        long id = this.sequenceGenerator.getAndIncrement() + 1;
        payload.setId(id);
        Observable<JsonRpcMessage> result = this.rpcResponseProcessor.awaitResponse(id, timeout);
        this.publish(hardwareControllerId, payload);
        return result;
    }

    @Scheduled(fixedDelay = 100)
    public void retryFailedMessages(){
        Set<Long> serialNumbers = this.failedMessages.keySet();
        serialNumbers.forEach(serialNumber -> {
            List<JsonRpcMessage> messages = this.failedMessages.get(serialNumber);
            this.failedMessages.remove(serialNumber);
            messages.forEach(message -> {
                try {
                    logger.debug("Retrying message: {}", message);
                    this.publish(serialNumber, message);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            });
        });
    }
}
