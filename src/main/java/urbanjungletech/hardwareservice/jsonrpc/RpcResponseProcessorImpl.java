package urbanjungletech.hardwareservice.jsonrpc;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class RpcResponseProcessorImpl implements RpcResponseProcessor{
    private final Map<Long, BehaviorSubject<JsonRpcMessage>> responses = new ConcurrentHashMap<>();
    @Override
    public Observable<JsonRpcMessage> awaitResponse(long id, long timeout){
        BehaviorSubject<JsonRpcMessage> subject = responses.computeIfAbsent(id, k -> BehaviorSubject.create());
        return subject.timeout(timeout, TimeUnit.MILLISECONDS);
    }
    @Override
    public void addResponse(JsonRpcMessage message){
        BehaviorSubject<JsonRpcMessage> subject = responses.get(message.getId());
        if (subject != null) {
            subject.onNext(message);
            this.responses.remove(message.getId());
        }
    }
}
