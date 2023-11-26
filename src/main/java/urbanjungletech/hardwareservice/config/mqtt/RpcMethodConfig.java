package urbanjungletech.hardwareservice.config.mqtt;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.jsonrpc.method.RpcMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RpcMethodConfig {

    @Bean("rpcMethods")
    public Map<String, RpcMethod> getRpcMethods(List<RpcMethod> rpcMethods){
        Map<String, RpcMethod> result = new HashMap<>();
        rpcMethods.stream().forEach((RpcMethod rpcMethod) -> {
            result.put(StringUtils.capitalise(rpcMethod.getClass().getSimpleName()), rpcMethod);
        });
        return result;
    }

}
