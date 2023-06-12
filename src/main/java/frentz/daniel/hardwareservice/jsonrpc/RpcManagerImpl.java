package frentz.daniel.hardwareservice.jsonrpc;

import frentz.daniel.hardwareservice.jsonrpc.method.RpcMethod;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RpcManagerImpl implements RpcManager{
    Logger logger = LoggerFactory.getLogger(RpcManagerImpl.class);

    private HashMap<String, RpcMethod> methods;
    private RpcResponseProcessor rpcResponseProcessor;

    public RpcManagerImpl(RpcResponseProcessor rpcResponseProcessor, List<RpcMethod> rpcMethods){
        this.rpcResponseProcessor = rpcResponseProcessor;
        this.methods = new HashMap<>();
        for(RpcMethod rpcMethod : rpcMethods){
            this.methods.put(rpcMethod.getName(), rpcMethod);
        }
    }

    @Override
    public void process(JsonRpcMessage jsonRpcMessage) {
        logger.debug("processing message ->{}");
        logger.debug("method: " + jsonRpcMessage.getMethod());
        logger.debug("params: " + jsonRpcMessage.getParams());
        logger.debug("response " + jsonRpcMessage.getResult());
        //if we have an id, then its a response.
        if(jsonRpcMessage.getId() != 0){
            this.rpcResponseProcessor.addResponse(jsonRpcMessage);
        }
        else {
            String method = jsonRpcMessage.getMethod();
            this.methods.get(method).process(jsonRpcMessage.getParams());
        }
    }


    @Override
    public void addRpcMethod(RpcMethod rpcMethod){
        this.methods.put(rpcMethod.getName(), rpcMethod);
    }
}
