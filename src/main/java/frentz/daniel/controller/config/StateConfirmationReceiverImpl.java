package frentz.daniel.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.controller.service.HardwareService;
import frentz.daniel.controllerclient.model.HardwareStateMessage;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class StateConfirmationReceiverImpl implements StateConfirmationReceiver{

    private HardwareService hardwareService;
    private ObjectMapper objectMapper;

    public StateConfirmationReceiverImpl(HardwareService hardwareService, ObjectMapper objectMapper){
        this.hardwareService = hardwareService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            HardwareStateMessage confirmation = this.objectMapper.readValue(json, HardwareStateMessage.class);
            this.hardwareService.setStateConfirmation(confirmation.getHardwareSerialNumber(),
                    confirmation.getPort(), confirmation.getState());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
