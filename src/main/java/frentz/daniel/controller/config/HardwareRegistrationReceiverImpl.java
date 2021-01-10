package frentz.daniel.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.controller.service.HardwareControllerService;
import frentz.daniel.controllerclient.model.HardwareRegistrationMessage;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class HardwareRegistrationReceiverImpl implements HardwareRegistrationReceiver{

    private HardwareControllerService hardwareControllerService;
    private ObjectMapper objectMapper;

    public HardwareRegistrationReceiverImpl(HardwareControllerService hardwareControllerService,
                                            ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.hardwareControllerService = hardwareControllerService;

    }

    @Override
    public void onMessage(Message message) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            HardwareRegistrationMessage hardwareRegistrationMessage = this.objectMapper.readValue(json, HardwareRegistrationMessage.class);
            long hardwareControllerId = this.hardwareControllerService.getHardwareControllerIdFromSerialNumber(hardwareRegistrationMessage.getHardwareSerialNumber());
            this.hardwareControllerService.addHardware(hardwareControllerId, hardwareRegistrationMessage.getHardware());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
