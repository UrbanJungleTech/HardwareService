package urbanjungletech.hardwareservice.config.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;

@Configuration
public class SerializationConfig {
    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper result = Jackson2ObjectMapperBuilder
                .json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
                .build();
        result.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        return result;
    }
}
