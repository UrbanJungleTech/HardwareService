package frentz.daniel.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.controller.config.HardwareRabbitConfig;
import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controllerclient.model.HardwareState;
import frentz.daniel.controllerclient.model.HardwareStateMessage;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class HardwareQueueServiceImpl implements HardwareQueueService {

    private ObjectMapper objectMapper;
    private HardwareRabbitConfig hardwareRabbitConfig;
    private AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    public HardwareQueueServiceImpl(ObjectMapper objectMapper,
                                    HardwareRabbitConfig hardwareRabbitConfig,
                                    AmqpAdmin amqpAdmin,
                                    RabbitTemplate rabbitTemplate){
        this.objectMapper = objectMapper;
        this.hardwareRabbitConfig = hardwareRabbitConfig;
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public String createStateChangeMessage(long port, HardwareState state, String serialNumber) throws JsonProcessingException {
        HardwareStateMessage message = new HardwareStateMessage();
        message.setPort(port);
        message.setState(state);
        message.setHardwareSerialNumber(serialNumber);
        String messageString = this.objectMapper.writeValueAsString(message);
        return messageString;
    }

    @Override
    public String getHardwareStateChangeQueue(String hardwareControllerSerialNumber){
        return this.hardwareRabbitConfig.getStateChange().getQueue() + "." + hardwareControllerSerialNumber;
    }

    @Override
    public void createHardwareStateChangeQueue(String hardwareControllerSerialNumber){
        String hardwareQueueName = this.getHardwareStateChangeQueue(hardwareControllerSerialNumber);
        Queue queue = new Queue(hardwareQueueName, false);
        amqpAdmin.declareQueue(queue);
    }

    @Override
    public void sendStateToController(HardwareControllerEntity hardwareControllerEntity, long port, HardwareState state){
        try {
            String messageString = this.createStateChangeMessage(port, state, hardwareControllerEntity.getSerialNumber());
            String queue = this.getHardwareStateChangeQueue(hardwareControllerEntity.getSerialNumber());
            this.rabbitTemplate.convertAndSend(this.hardwareRabbitConfig.getStateChange().getExchange(), queue, messageString);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
