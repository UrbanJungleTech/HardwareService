package frentz.daniel.hardwareservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ObjectLoggerServiceImpl implements ObjectLoggerService{

    private ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(ObjectLoggerServiceImpl.class);


    ObjectLoggerServiceImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void logInfo(String message, Object entity) {
        try{
            String serializedEntity = this.objectMapper.writeValueAsString(entity);
            this.logger.info(message);
            this.logger.info(serializedEntity);
        }
        catch(Exception ex){
            logger.info("Problem serializing entity");
        }
    }
}
