package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.HardwareController;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareControllerSubscriptionServiceImpl implements HardwareControllerSubscriptionService{

    private List<SseEmitter> subscriptions;

    public HardwareControllerSubscriptionServiceImpl(){
        this.subscriptions = new ArrayList<>();
    }

    private void notify(String eventName, Object data){
        this.subscriptions.stream().forEach((subscription) -> {
            try {
                subscription.send(SseEmitter.event().name(eventName).data(data).build());
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void notifyHardwareControllerAdded(HardwareController hardwareController) {
        this.notify("hardwareControllerAdded", hardwareController);
    }

    @Override
    public void notifyHardwareControllerRemoved(long id) {
        this.notify("hardwareControllerRemoved", id);
    }

    @Override
    public SseEmitter addSubscriber() {
        SseEmitter result = new SseEmitter();
        this.subscriptions.add(result);
        result.onCompletion(() -> {
            this.subscriptions.remove(result);
        });
        return result;
    }
}
