package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HardwareControllerCommunicationService {
    String type();

    boolean custom() default false;
}
