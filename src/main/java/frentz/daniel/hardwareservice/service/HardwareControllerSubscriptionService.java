package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.HardwareController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface HardwareControllerSubscriptionService {
    void notifyHardwareControllerAdded(HardwareController hardwareController);
    void notifyHardwareControllerRemoved(long id);
    SseEmitter addSubscriber();
}
