package urbanjungletech.hardwareservice.config.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

import java.time.format.DateTimeFormatter;
import java.util.Set;

@Configuration
public class SerializationConfig {

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper result = Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
                .build();

        Reflections superTypesReflection = new Reflections("urbanjungletech");
        Set<Class<?>> superTypes = superTypesReflection.getTypesAnnotatedWith(JsonTypeInfo.class);
        superTypes.stream().forEach(superType -> {
            Reflections subTypesReflection = new Reflections("urbanjungletech");
            var subTypes = subTypesReflection.getSubTypesOf(superType);
            subTypes.stream().forEach(subType -> {
                result.registerSubtypes(new NamedType(subType, subType.getSimpleName()));
            });
        });
        return result;
    }
}
