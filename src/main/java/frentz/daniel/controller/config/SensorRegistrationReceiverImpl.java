package frentz.daniel.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.controller.service.HardwareControllerService;
import frentz.daniel.controllerclient.model.SensorRegistrationMessage;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
public class SensorRegistrationReceiverImpl implements SensorRegistrationReceiver{
    private HardwareControllerService hardwareControllerService;
    private ObjectMapper objectMapper;

    public SensorRegistrationReceiverImpl(HardwareControllerService hardwareControllerService,
                                          ObjectMapper objectMapper){
        this.hardwareControllerService = hardwareControllerService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String json = new String(message.getBody());
            SensorRegistrationMessage registrationMessage = this.objectMapper.readValue(json, SensorRegistrationMessage.class);
            long hardwareControllerId = this.hardwareControllerService.getHardwareControllerIdFromSerialNumber(registrationMessage.getHardwareSerialNumber());
            this.hardwareControllerService.addSensor(hardwareControllerId, registrationMessage.getSensor());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
