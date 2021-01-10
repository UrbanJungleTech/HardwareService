package frentz.daniel.controller.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.controller.config.SensorRabbitConfig;
import frentz.daniel.controller.entity.SensorEntity;
import frentz.daniel.controllerclient.model.SensorReadingMessage;
import frentz.daniel.controllerclient.model.SensorResult;
import frentz.daniel.controllerclient.model.SensorType;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SensorQueueServiceImpl implements SensorQueueService{

    private SensorRabbitConfig sensorRabbitConfig;
    private AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public SensorQueueServiceImpl(SensorRabbitConfig sensorRabbitConfig,
                                  AmqpAdmin amqpAdmin,
                                  RabbitTemplate rabbitTemplate,
                                  ObjectMapper objectMapper){
        this.sensorRabbitConfig = sensorRabbitConfig;
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public void createSensorReadingQueue(String hardwareControllerSerialNumber){
        String hardwareQueueName = this.getSensorReadingQueue(hardwareControllerSerialNumber);
        Queue queue = new Queue(hardwareQueueName, false);
        amqpAdmin.declareQueue(queue);
    }

    @Override
    public String getSensorReadingQueue(String hardwareControllerSerialNumber){
        return this.sensorRabbitConfig.getReading().getQueue() + "." + hardwareControllerSerialNumber;
    }

    @Override
    public SensorResult getSensorReading(SensorEntity sensorEntity, SensorType[] sensorTypes) {
        try {
            String queue = this.getSensorReadingQueue(sensorEntity.getHardwareController().getSerialNumber());
            SensorReadingMessage message = new SensorReadingMessage();
            message.setSensorSerialNumber(sensorEntity.getSerialNumber());
            message.setSensorTypes(sensorTypes);
            String messageJson = this.objectMapper.writeValueAsString(message);
            SensorResult sensorResult = (SensorResult) this.rabbitTemplate.convertSendAndReceive(queue, messageJson);
            return sensorResult;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
