package urbanjungletech.hardwareservice.service.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.jsonrpc.RpcResponseProcessor;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.service.HardwareControllerService;
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
    private final Map<Long, List<MqttMessage>> failedMessages;
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
    public void publish(long hardwareControllerId, JsonRpcMessage rpcMessage) {
        MqttMessage message = null;
        try {
            String payload = this.objectMapper.writeValueAsString(rpcMessage);
            message = new MqttMessage(payload.getBytes());
            message.setQos(2);
            message.setRetained(true);
            this.mqttClient.publish(hardwareControllerId, message);
            logger.debug("Sent RPC message -> {} ", message);
        }
        catch(Exception ex){
            Optional.of(message).ifPresent((msg) -> {
                if(!failedMessages.containsKey(hardwareControllerId)){
                    List<MqttMessage> messages = new ArrayList<>();
                    failedMessages.put(hardwareControllerId, messages);
                }
                else{
                    failedMessages.get(hardwareControllerId).add(msg);
                }
            });
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
