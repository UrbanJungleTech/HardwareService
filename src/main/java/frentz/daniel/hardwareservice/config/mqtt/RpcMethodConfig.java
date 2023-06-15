package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.jsonrpc.method.RpcMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RpcMethodConfig {

    @Bean("rpcMethods")
    public Map<String, RpcMethod> getRpcMethods(RpcProperties rpcProperties, List<RpcMethod> rpcMethods){
        Map<String, RpcMethod> result = new HashMap<>();
        for(RpcMethod rpcMethod : rpcMethods){
            if(rpcProperties.getMethods().containsKey(rpcMethod.getClass().getCanonicalName())){
                String methodName = rpcProperties.getMethods().get(rpcMethod.getClass().getCanonicalName());
                result.put(methodName, rpcMethod);
            }
        }
        return result;
    }

}
