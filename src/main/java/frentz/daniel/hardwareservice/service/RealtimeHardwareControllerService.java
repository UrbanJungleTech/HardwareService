package frentz.daniel.hardwareservice.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface RealtimeHardwareControllerService {
    SseEmitter getRealtimeHardwareController();
}
