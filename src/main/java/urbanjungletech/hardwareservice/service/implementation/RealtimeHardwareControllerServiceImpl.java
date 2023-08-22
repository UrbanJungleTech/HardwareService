package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.service.HardwareControllerSubscriptionService;
import urbanjungletech.hardwareservice.service.RealtimeHardwareControllerService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
public class RealtimeHardwareControllerServiceImpl implements RealtimeHardwareControllerService {

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
        List<HardwareControllerEntity> hardwareControllers = this.hardwareControllerDAO.getAllHardwareControllers();
        try {
            result.send(SseEmitter.event().name("hardwareControllers").data(hardwareControllers).build());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
