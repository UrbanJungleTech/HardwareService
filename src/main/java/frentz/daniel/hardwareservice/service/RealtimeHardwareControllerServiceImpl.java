package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
public class RealtimeHardwareControllerServiceImpl implements RealtimeHardwareControllerService{

    private HardwareControllerSubscriptionService hardwareControllerSubscriptionService;
    private HardwareControllerDAO hardwareControllerDAO;

    public RealtimeHardwareControllerServiceImpl(HardwareControllerSubscriptionService hardwareControllerSubscriptionService,
                                                 HardwareControllerDAO hardwareControllerDAO){
        this.hardwareControllerSubscriptionService = hardwareControllerSubscriptionService;
        this.hardwareControllerDAO = hardwareControllerDAO;
    }

    @Override
    public SseEmitter getRealtimeHardwareController() {
        SseEmitter result = this.hardwareControllerSubscriptionService.addSubscriber();
        List<HardwareControllerEntity> hardwareControllers = this.hardwareControllerDAO.getAllHardware();
        try {
            result.send(SseEmitter.event().name("hardwareControllers").data(hardwareControllers).build());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
