package urbanjungletech.hardwareservice.jsonrpc;

import urbanjungletech.hardwareservice.exception.RpcMethodNotFoundException;
import urbanjungletech.hardwareservice.jsonrpc.method.RpcMethod;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RpcManagerImpl implements RpcManager{
    Logger logger = LoggerFactory.getLogger(RpcManagerImpl.class);

    private Map<String, RpcMethod> methods;
    private RpcResponseProcessor rpcResponseProcessor;

    public RpcManagerImpl(RpcResponseProcessor rpcResponseProcessor,
                          @Qualifier("rpcMethods")Map<String, RpcMethod> methods){
        this.rpcResponseProcessor = rpcResponseProcessor;
        this.methods = methods;
    }

    /**
     * Processes the given JsonRpcMessage
     * if the id is 0, then it is a request and the method is looked up and called.
     * if the id is not 0, then it is a response and the response is added to the response processor to be picked up by another process
     * @param jsonRpcMessage
     */
    @Override
    public void process(JsonRpcMessage jsonRpcMessage) {
        logger.debug("processing message ->{}");
        logger.debug("method: {}", jsonRpcMessage.getMethod());
        logger.debug("params: {}", jsonRpcMessage.getParams());
        logger.debug("response: {}", jsonRpcMessage.getResult());
        //if we have an id, then its a response.
        if(jsonRpcMessage.getId() != 0){
            this.rpcResponseProcessor.addResponse(jsonRpcMessage);
        }
        else {
            String method = jsonRpcMessage.getMethod();
            RpcMethod methodToCall = this.methods.get(method);
            if(methodToCall == null){
                throw new RpcMethodNotFoundException(method);
            }
            methodToCall.process(jsonRpcMessage.getParams());
        }
    }
}
