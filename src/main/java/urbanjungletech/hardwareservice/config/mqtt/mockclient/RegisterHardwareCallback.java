package urbanjungletech.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import urbanjungletech.hardwareservice.jsonrpc.model.RegisterHardwareMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterHardwareCallback implements MockMqttClientCallback{
    private static final Logger logger = LoggerFactory.getLogger(RegisterHardwareCallback.class);
    private List<RegisterHardwareMessage> registerHardwareMessages;
    private ObjectMapper objectMapper;
    public RegisterHardwareCallback(
            ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.registerHardwareMessages = new ArrayList<>();
    }

    @Override
    public void callback(JsonRpcMessage message) {
        logger.debug("Received register hardware message and saving it to the list.");
        RegisterHardwareMessage registerHardwareMessage = this.objectMapper.convertValue(message, RegisterHardwareMessage.class);
        this.registerHardwareMessages.add(registerHardwareMessage);
    }

    public List<RegisterHardwareMessage> getRegisterHardwareMessages(){
        return this.registerHardwareMessages;
    }

    public void clear(){
        this.registerHardwareMessages.clear();
    }
}
